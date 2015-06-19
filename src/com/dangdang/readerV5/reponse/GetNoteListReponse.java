package com.dangdang.readerV5.reponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-6-19.
 */
public class GetNoteListReponse {
    List<NoteSimpleInfo> noteList;

    public List<NoteSimpleInfo> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<NoteSimpleInfo> noteList) {
        this.noteList = noteList;
    }
}
