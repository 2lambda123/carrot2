import React, { useCallback } from 'react';

import "./FileSetting.css";

import filesize from "filesize";

import { view, store } from "@risingstack/react-easy-state";
import { Setting } from "./Settings.js";
import { useDropzone } from "react-dropzone";
import { Button } from "@blueprintjs/core";

export const FileSetting = view(({ setting, get, set }) => {
  const { label, description } = setting;
  const currentFile = store({ file: null });

  const onDrop = useCallback(acceptedFiles => {
    if (acceptedFiles.length > 0) {
      const file = acceptedFiles[0];
      currentFile.file = file;
      set(setting, file);
    }
  }, [ set, setting ]);
  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    multiple: false
  });

  const fileInfo = currentFile.file ?
      <div className="FileSettingFileInfo">
        <span>{currentFile.file.name}</span>
        <span>{currentFile.file.type}</span>
        <span>{filesize(currentFile.file.size)}</span>
      </div>
      :
      null;

  return (
      <Setting className="FileSetting" label={label} description={description}>
        <div {...getRootProps()}>
          <input {...getInputProps()} />
          <div className="FileSettingDropZone">
            {fileInfo}
            <div>
              <Button small={true}>Browse</Button> or drag 'n' drop your file here.
            </div>
          </div>
        </div>
      </Setting>
  );
});