package com.avpc.avpcmobile.adapter.message;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avpc.avpcmobile.R;
import com.avpc.model.Message;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {


    private static final String TAG = MessageListAdapter.class.getSimpleName();

    private List<Message> mMessagesList;
    private MessageItemListener mListener;
    private Context mContext;

    public MessageListAdapter(Context context, List<Message> listMessages, MessageItemListener listener) {
        mMessagesList = listMessages;
        mListener = listener;
        mContext = context;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_messages_list, parent, false);
        MessageViewHolder viewHolder = new MessageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message currentObj = mMessagesList.get(position);
        holder.setData(currentObj, position);
    }

    public void replaceData(List<Message> messages) {
        setList(messages);
        notifyDataSetChanged();
    }

    public void setList(List<Message> messages) {
        mMessagesList = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayoutCompat layout;
        ImageView messageImage;
        TextView messageName, messageDistance, messageAvailability;
        Button btnTest;
        private int mPosition;
        private Message mMessage;

        public MessageViewHolder(View itemView) {
            super(itemView);

//            layout = (LinearLayoutCompat) itemView.findViewById(R.id.messages_list_line);
//            messageImage = (ImageView) itemView.findViewById(R.id.messages_list_image);
//            messageName = (TextView) itemView.findViewById(R.id.messages_list_message_name);
//            messageDistance = (TextView) itemView.findViewById(R.id.messages_list_distance);
//            messageAvailability = (TextView) itemView.findViewById(R.id.messages_list_availability);
            itemView.setOnClickListener(this);

        }

        public void setData(Message currentObj, int position) {
//            Picasso.with(mContext).load(currentObj.getPhotoURL()).into(messageImage);
//            messageName.setText(currentObj.getName());
            mMessage = currentObj;
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            mListener.onMessageClick(mMessage);
        }
    }

}
