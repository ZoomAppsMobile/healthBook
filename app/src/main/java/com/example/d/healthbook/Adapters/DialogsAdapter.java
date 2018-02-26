package com.example.d.healthbook.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Models.Chat.Chat;
import com.example.d.healthbook.Models.Chat.DialogModel;
import com.example.d.healthbook.Models.ResponseGetUserData;
import com.example.d.healthbook.R;
import com.example.d.healthbook.ViewHolders.ViewHolderChatGroupModels;
import com.example.d.healthbook.ViewHolders.ViewHolderChatModel;
import com.example.d.healthbook.View.DialogsView;

import java.util.List;

import io.realm.Realm;

import static com.example.d.healthbook.Controller.MainController.getNormalTime;

/**
 * Created by User on 21.08.2017.
 */

public class DialogsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG1 = "MY LIST ADAPTER";
    private Context context;
    private List<DialogModel> documents;
    private final int GROUP_MODEL = 0, CHAT_MODEL = 1;
    private Realm mRealm;


    public DialogsAdapter(List<DialogModel> datum, Context context) {
        documents = datum;
        this.context = context;
        mRealm = Realm.getInstance(context);


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case CHAT_MODEL:
                View v1 = inflater.inflate(R.layout.row_item_one_man_chat, parent, false);
                viewHolder = new ViewHolderChatModel(v1);
                break;
            case GROUP_MODEL:
                View v2 = inflater.inflate(R.layout.row_item_group_already_added, parent, false);
                viewHolder = new ViewHolderChatGroupModels(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case CHAT_MODEL:
                ViewHolderChatModel vh1 = (ViewHolderChatModel) holder;
                configureViewHolder1(vh1, position);
                break;
            case GROUP_MODEL:
                ViewHolderChatGroupModels vh2 = (ViewHolderChatGroupModels) holder;
                configureViewHolder2(vh2, position);
                break;

        }


    }

    private void configureViewHolder2(ViewHolderChatGroupModels vh2, int position) {
//        int size = documents.get(position).getGroupModel().size();
//        vh2.getTitleGroup().setText(documents.get(position).getGroupModel().get(size - 1).getTitleGroup());
//        vh2.getListPeopleGroup().setText(documents.get(position).getGroupModel().get(size - 1).getAllGroupPeople());
//
//
//        vh2.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void configureViewHolder1(ViewHolderChatModel vh1, final int position) {

//        int size = documents.get(position).getChatModels().size();
//
//        int quantityUnreadMessage = 0;
        Chat chatData = documents.get(position).getChatData();
        ResponseGetUserData userData = documents.get(position).getUsersData().get(0);
        vh1.getNameSurnameChatMan().setText(
                String.format(
                        "%s %s %s",
                                    MainController.stringChecker(userData.getName()),
                                    MainController.stringChecker(userData.getMiddleName()),
                                    MainController.stringChecker(userData.getSurname())));
//        vh1.getImageChatMan().setImageBitmap(MainController.decodeSampledBitmapFromResource(context.getResources(),
//                userData.getA(), 100, 100));
        MainController.setImageToViewById(context,
                userData.getPhoto(),
                vh1.getImageChatMan());
        vh1.getTimeChat().setText(getNormalTime(chatData.getUpdatedAt()));
        vh1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DialogsView)context).onDialogClicked(position);
//                Intent intent = new Intent(context, ChatActivityShowMessage.class);
//                intent.putExtra("fromRecycler", true);
//                intent.putExtra("name",documents.get(position).getNameSurname());
//                intent.putExtra("position", position);
//                intent.putExtra("img",documents.get(position).getImageMan());
//                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        String type = documents.get(position).getChatData().getType();
        if (type.equals("dialog")) {
            return CHAT_MODEL;
        } else {
            return GROUP_MODEL;
        }
      //  return -1;
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }


}
