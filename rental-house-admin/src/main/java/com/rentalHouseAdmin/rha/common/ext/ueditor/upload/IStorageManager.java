package com.rentalHouseAdmin.rha.common.ext.ueditor.upload;

import com.rentalHouseAdmin.rha.common.ext.ueditor.define.State;

import java.io.InputStream;


public interface IStorageManager {

    com.rentalHouseAdmin.rha.common.ext.ueditor.define.State saveBinaryFile(byte[] data, String rootPath, String savePath);

    com.rentalHouseAdmin.rha.common.ext.ueditor.define.State saveFileByInputStream(InputStream is, String rootPath, String savePath);

    State saveFileByInputStream(InputStream is, String rootPath, String savePath, long maxSize);
}
