package com.wilddog.newchat;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.wilddog.client.Query;

/**
 * @author Jeen
 * @since 9/16/15
 *
 * This class is an example of how to use WilddogListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
//public class ChatListAdapter extends WilddogListAdapter<Chat> {
public class ChatListAdapter extends MessagesAdapter<Chat> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;
//            mWilddogRef.limitToLast(50), this,        R.layout.chat_message, mUsername);
    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Chat.class, layout, activity);
        this.mUsername = mUsername;
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>WilddogListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * //@param view A view instance corresponding to the layout we passed to the constructor.
     * @param chat An instance representing the current state of a chat message
     */
    /*
    @Override
    protected void populateView(Context context, View view, Chat chat) {
        // Map a Chat object to an entry in our listview
        // 设置发送者
        String author = chat.getAuthor();
        TextView authorText = (TextView) view.findViewById(R.id.author);
        authorText.setText(author + ": ");
        // If the message was sent by this user, color it differently
        // 根据是不是自己发的，改变 发送者 颜色
        if (author != null && author.equals(mUsername)) {
            authorText.setTextColor(Color.RED);
        } else {
            authorText.setTextColor(Color.BLUE);
        }
        // 设置信息
        ((TextView) view.findViewById(R.id.message)).setText(chat.getMessage());
    }
    */
    @Override
    protected void populateView(Context context, MessageViewHolder holder, Chat chat) {
        // 设置发送者
        String author = chat.getAuthor();
        // 根据是不是自己发的，改变 发送者 颜色
        if (author != null && author.equals(mUsername)) {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(chat.getMessage());
        } else {
            // 如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(chat.getMessage());
        }
    }
}
