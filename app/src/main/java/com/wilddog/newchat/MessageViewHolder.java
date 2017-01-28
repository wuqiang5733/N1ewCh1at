package com.wilddog.newchat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    // 与 SentMessagesActivity 对应
//    private ImageView _avatar;
//    private TextView _displayName;
//    private TextView _createdAt;
//    private CardView _cardView;
//    private TextView _sentReceived;
//    private View _backgroundView;
    LinearLayout leftLayout;

    LinearLayout rightLayout;

    TextView leftMsg;

    TextView rightMsg;

    public MessageViewHolder(LayoutInflater inflater, ViewGroup parent) {
//        super(inflater.inflate(R.layout.list_item_message, parent, false));
        super(inflater.inflate(R.layout.msg_item, parent, false));
//        _cardView = (CardView) itemView;
//        _avatar = (ImageView) itemView.findViewById(R.id.list_item_message_avatar);
//        _displayName = (TextView) itemView.findViewById(R.id.list_item_message_displayName);
//        _createdAt = (TextView) itemView.findViewById(R.id.list_item_message_createdAt);
//        _sentReceived = (TextView) itemView.findViewById(R.id.list_item_message_sentReceived);
//        _backgroundView = itemView.findViewById(R.id.list_item_message_background);
//        super(view);
        leftLayout = (LinearLayout) itemView.findViewById(R.id.left_layout);
        rightLayout = (LinearLayout) itemView.findViewById(R.id.right_layout);
        leftMsg = (TextView) itemView.findViewById(R.id.left_msg);
        rightMsg = (TextView) itemView.findViewById(R.id.right_msg);
    }
/*
    public void populate(Context context, Message message) {
        _backgroundView.setTag(message);

        Picasso.with(context)
               .load(message.getOtherUser().getAvatarUrl())
               .into(_avatar);

        String createdAt = DateUtils.formatDateTime(
                context,
                message.getCreatedAt().getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);

        _sentReceived.setText(message.isFromUs() ? "sent " : "received ");
        _displayName.setText(message.getOtherUser().getDisplayName());
        _createdAt.setText(createdAt);

        int colorResourceId;
        if (message.isSelected()) {
            colorResourceId = R.color.list_item_message_background_selected;
            _cardView.setCardElevation(5);
        } else if (message.isRead()) {
            colorResourceId = R.color.list_item_message_background;
            _cardView.setCardElevation(2);
        } else {
            colorResourceId = R.color.list_item_message_background_unread;
            _cardView.setCardElevation(3);
        }

        _cardView.setCardBackgroundColor(context.getResources().getColor(colorResourceId));
    }

    public void setOnClickListener(View.OnClickListener listener) {
        _backgroundView.setOnClickListener(listener);
    }*/
}
