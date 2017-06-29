package com.avpc.avpcmobile.server;

import android.os.AsyncTask;

import com.avpc.model.Member;

/**
 * Created by thiago on 07/06/17.
 */

public class DataLoader {

    private class HttpRequestMemberTask extends AsyncTask<Void, Void, Member>{

        @Override
        protected Member doInBackground(Void... params) {



            return null;
        }

        @Override
        protected void onPostExecute(Member member) {

        }
    }

}
