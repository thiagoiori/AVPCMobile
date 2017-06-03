package com.avpc.avpcmobile;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.avpc.avpcmobile.adapter.UIMembersCursorAdapter;
import com.avpc.avpcmobile.presenter.MemberPresenter;

public class MemberFragment extends ListFragment {

    private ListView lisTasks;
    private MemberPresenter presenter;
    private UIMembersCursorAdapter memberCursorAdapter;

    private OnMemberListListener mListener;

    public MemberFragment() {}

    public static MemberFragment newInstance() {
        MemberFragment fragment = new MemberFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MemberPresenter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_member, container, false);
        View view = super.onCreateView(inflater, container, savedInstanceState);
        //bindViews(view);
        //bindListeners();
        showMembers();
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mListener != null){
            mListener.onMemberSelected(id);
        }
    }

//    private void bindListeners() {
//        lisTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mListener.onMemberSelected(id);
//            }
//        });
//    }

    private void showMembers() {
        Cursor cursorMembers = presenter.getMembers();
        if (cursorMembers.getCount() > 0) {
            if(memberCursorAdapter == null) {
                memberCursorAdapter = new UIMembersCursorAdapter(getActivity(), cursorMembers, 0);
                memberCursorAdapter.setLatitude(0);
                memberCursorAdapter.setLongitude(0);
                //lisTasks.setAdapter(memberCursorAdapter);
                setListAdapter(memberCursorAdapter);
            }
            else
            {
                memberCursorAdapter.changeCursor(cursorMembers);
                memberCursorAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMemberListListener) {
            mListener = (OnMemberListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMemberListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMemberListListener {
        void onMemberSelected(long memberId);
    }

//    private void bindViews(View view) {
//        lisTasks = (ListView) view.findViewById(R.id.list_members);
//    }
}
