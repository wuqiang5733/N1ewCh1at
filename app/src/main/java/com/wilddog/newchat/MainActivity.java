package com.wilddog.newchat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wilddog.client.DataSnapshot;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.WilddogSync;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String mUsername;
    private SyncReference mWilddogRef;
    private ValueEventListener mConnectedListener;
    private com.wilddog.newchat.ChatListAdapter mChatListAdapter;
    /************************/
    private MessagesAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("XIAOXIAO");
        // Make sure we have a mUsername
        setupUsername();

//        setTitle("Chatting as " + mUsername);

        // Setup our Wilddog mWilddogRef
        mWilddogRef = WilddogSync.getInstance().getReference().child("chat");


        // Setup our input methods. Enter key on the keyboard or pushing the send button
        EditText inputText = (EditText) findViewById(R.id.messageInput);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
//        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
//        mChatListAdapter = new com.wilddog.newchat.ChatListAdapter(mWilddogRef.limitToLast(50), this, R.layout.chat_message, mUsername);
        mChatListAdapter = new com.wilddog.newchat.ChatListAdapter(mWilddogRef.limitToLast(50), this, R.layout.msg_item, mUsername);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_sent_messages_messages);
        recyclerView.setAdapter(mChatListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        listView.setAdapter(mChatListAdapter);
//        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
//            @Override
//            public void onChanged() {
//                super.onChanged();
//                listView.setSelection(mChatListAdapter.getCount() - 1);
//            }
//        });
        mChatListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                recyclerView.scrollToPosition((mChatListAdapter.getItemCount() - 1));
            }
        });

        // Finally, a little indication of connection status
        mConnectedListener = mWilddogRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
//                    Toast.makeText(MainActivity.this, "Connected to Wilddog", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "联接到服务器", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(MainActivity.this, "Disconnected from Wilddog", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "已从服务器断开", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(SyncError wilddogError) {
                // No-op
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        //  .info/connected 是 Wilddog Sync 提供的一个保留路径，用于存储客户端与云端的连接状态。
        mWilddogRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();
    }

    private void setupUsername() {
        SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        mUsername = prefs.getString("username", null);
        if (mUsername == null) {
            Random r = new Random();
            // Assign a random user name if we don't have one saved.
            mUsername = "xiaoxiao" + r.nextInt(100000);
            prefs.edit().putString("username", mUsername).commit();
        }
    }

    private void sendMessage() {
        EditText inputText = (EditText) findViewById(R.id.messageInput);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, mUsername);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mWilddogRef.push().setValue(chat);
            inputText.setText("");
        }
    }
}
