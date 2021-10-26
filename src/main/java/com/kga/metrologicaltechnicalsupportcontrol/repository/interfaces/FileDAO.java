package com.kga.metrologicaltechnicalsupportcontrol.repository.interfaces;


import com.kga.metrologicaltechnicalsupportcontrol.model.FileInfo;

public interface FileDAO  {
    FileInfo create(FileInfo file);
    FileInfo findById(Long fileId);
    void delete(Long fileId);
}
