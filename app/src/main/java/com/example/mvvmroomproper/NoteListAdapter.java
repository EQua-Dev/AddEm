package com.example.mvvmroomproper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    public interface OnDeleteClickListener{
        void OnDeleteClickListener(Note myNote);
    }

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Note> mNotes;
    private OnDeleteClickListener onDeleteClickListener;
//    public NoteListAdapter(List<Note> list){
//        this.list = list;
//    }

    public NoteListAdapter(Context context, OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context) ;
        mContext = context;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
         NoteViewHolder noteViewHolder = new NoteViewHolder(itemView);
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (mNotes != null){
            Note note = mNotes.get(position);
            holder.setData(note.getNote(),position);
            holder.setListeners();
        }else {
            holder.noteItemView.setText(R.string.new_note);
        }
    }

    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }

    public void setNotes(List<Note> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView noteItemView;
        private int mPosition;
        private ImageView imgDelete, imgEdit;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            imgDelete = itemView.findViewById(R.id.ivRowDelete);
            imgEdit = itemView.findViewById(R.id.ivRowEdit);
        }

        public void setData(String note, int position) {
            noteItemView.setText(note);
            mPosition = position;
        }
        public void setListeners(){
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, EditNoteActivity.class);
                    intent.putExtra("note_id", mNotes.get(mPosition).getId());
                    ((Activity)mContext).startActivityForResult(intent, MainActivity.UPDATE_NEW_NOTE_ACTIVITY_REQUEST_CODE);
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDeleteClickListener != null){
                        onDeleteClickListener.OnDeleteClickListener(mNotes.get(mPosition));
                    }
                }
            });
        }
    }
}
