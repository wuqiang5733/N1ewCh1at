package com.wilddog.newchat;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wilddog.client.ChildEventListener;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.Query;
import com.wilddog.client.SyncError;

import java.util.ArrayList;
import java.util.List;

public abstract class MessagesAdapter<T>  extends RecyclerView.Adapter<MessageViewHolder> implements View.OnClickListener {
    // 与 SentMessagesActivity 对应
    private final LayoutInflater _layoutInflater;
//    private final BaseActivity _activity;
    private final Activity _activity;
//    private final OnMessageClickedListener _listener;
//    private final ArrayList<Message> _messages;
    /**********************************************************/
    private Query mRef;
    private Class<T> mModelClass;
    private int mLayout;
    private LayoutInflater mInflater;
    private List<T> mModels;
    private List<String> mKeys;
    private ChildEventListener mListener;

//    public MessagesAdapter(BaseActivity activity, OnMessageClickedListener listener) {
//    public MessagesAdapter(OnMessageClickedListener listener,  Query mRef, Class<T> mModelClass, int mLayout, Activity activity) {
    public MessagesAdapter(Query mRef, Class<T> mModelClass, int mLayout, Activity activity) {
        _activity = activity;
//        _listener = listener;
//        _messages = new ArrayList<>();
        _layoutInflater = activity.getLayoutInflater();

        /****************************************************************************/
        this.mRef = mRef;
        this.mModelClass = mModelClass;
        this.mLayout = mLayout;
        mInflater = activity.getLayoutInflater();
        mModels = new ArrayList<T>();
        mKeys = new ArrayList<String>();
        // Look for all child events. We will then map them to our own internal ArrayList, which backs ListView
        mListener = this.mRef.addChildEventListener(new ChildEventListener() {
            @Override
            // 一个添加了listener的节点，当有子节点被添加时触发此方法。
            // DataSnapshot 新添加的子节点数据快照。
            // String 排在被添加的新子节点前面的兄弟节点的key值。如果被添加的是当前节点的第一个子节点，该值为null。
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                // Object getValue() 从快照中获得当前节点的数据。
//                T model = (T) dataSnapshot.getValue(WilddogListAdapter.this.mModelClass);
                T model = (T) dataSnapshot.getValue(Chat.class); // 奇迹就这样发生了 。。。。
                Log.d("model_model",model.getClass().toString());
                Log.d("model_model",model.toString());
                // getKey()  获取当前节点的名称。从快照中，获取当前节点的名称。
                String key = dataSnapshot.getKey();

                // Insert into the correct location, based on previousChildName
                if (previousChildName == null) { // 如果这是第一个节点的话
                    mModels.add(0, model);
                    mKeys.add(0, key);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == mModels.size()) {
                        mModels.add(model);  // ？ 这个是会自动变长 ?
                        mKeys.add(key);
                    } else {
                        mModels.add(nextIndex, model);
                        mKeys.add(nextIndex, key);
                    }
                }

                notifyDataSetChanged();
            }

            @Override
            // 当前节点的子节点发生改变的时候触发此方法。
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // One of the mModels changed. Replace it in our list and name mapping
                // getKey()  获取当前节点的名称。
                String key = dataSnapshot.getKey();
//                T newModel = (T) dataSnapshot.getValue(WilddogListAdapter.this.mModelClass);
                T newModel = (T) dataSnapshot.getValue(Chat.class);
                int index = mKeys.indexOf(key);

                mModels.set(index, newModel);

                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                // A model was removed from the list. Remove it from our list and the name mapping
                String key = dataSnapshot.getKey();
                int index = mKeys.indexOf(key);

                mKeys.remove(index);
                mModels.remove(index);

                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

                // A model changed position in the list. Update our list accordingly
                String key = dataSnapshot.getKey();
//                T newModel = (T) dataSnapshot.getValue(WilddogListAdapter.this.mModelClass);
                T newModel = (T) dataSnapshot.getValue(Chat.class);
                int index = mKeys.indexOf(key);
                mModels.remove(index);
                mKeys.remove(index);
                if (previousChildName == null) {
                    mModels.add(0, newModel);
                    mKeys.add(0, key);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == mModels.size()) {
                        mModels.add(newModel);
                        mKeys.add(key);
                    } else {
                        mModels.add(nextIndex, newModel);
                        mKeys.add(nextIndex, key);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(SyncError syncError) {
                Log.e("WilddogListAdapter", "Listen was cancelled, no more updates will occur");
            }

        });
    }
    public void cleanup() {
        // 当listener在服务端失败，或者被删除的时候调用该方法。
        // We're being destroyed, let go of our mListener and forget about all of the mModels
        mRef.removeEventListener(mListener);
        mModels.clear();
        mKeys.clear();
    }

//    public ArrayList<Message> getMessages() {
//        return _messages;
//    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MessageViewHolder viewHolder = new MessageViewHolder(_layoutInflater, parent);
//        viewHolder.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
//        Message message = _messages.get(position);
//        holder.populate(_activity, message);
        /**********************************************************/
        T model = mModels.get(position);
//        holder.populate(_activity, model);
        populateView(_activity,model);
    }

    @Override
    public int getItemCount() {
//        return _messages.size();
        return mModels.size();
    }

    @Override
    public void onClick(View view) {
//        if (view.getTag() instanceof Message) {
//            Message message = (Message) view.getTag();
//            _listener.onMessageClicked(message);
//        }
    }

//    public interface OnMessageClickedListener {
//        void onMessageClicked(Message message);
//    }

    protected abstract void populateView(Context context, T model);
}
