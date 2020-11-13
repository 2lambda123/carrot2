import React, { useEffect, useState } from "react";

import "./Setting.css";

import { FormGroup, Popover, PopoverPosition } from "@blueprintjs/core";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faQuestionCircle } from "@fortawesome/pro-regular-svg-icons";

export const LabelWithHelp = ({ label, description }) => {
  if (description) {
    return (
        <div className="LabelWithHelp">
          {label}
          <DescriptionPopover description={description} />
        </div>
    );
  } else {
    return label;
  }
};

export const DescriptionPopover = ({ description }) => {
  const [ open, setOpen ] = useState(false);
  useEffect(() => {
    const listener = e => {
      if (e.keyCode === 27) {
        setOpen(false);
      }
    };
    document.body.addEventListener("keydown", listener)
    return () => {
      document.body.removeEventListener("keydown", listener);
    };
  }, []);
  return (
      <Popover content={<SettingDescription description={description} />}
               position={PopoverPosition.TOP} canEscapeKeyClose={true}
               isOpen={open}
               onInteraction={setOpen}>
        <FontAwesomeIcon className="HelpIcon" icon={faQuestionCircle} />
      </Popover>
  );
};

const SettingDescription = ({ description }) => {
  return <div className="SettingDescription" dangerouslySetInnerHTML={{ __html: description }} />;
};

export const Setting = ({ className, inline = false, label, description, children }) => {
  return (
      <FormGroup className={`${className} Setting`} inline={inline}
                 label={<LabelWithHelp label={label} description={description} />}>
        {children}
      </FormGroup>
  )
};

export const storeAccessors = (store, property) => {
  return {
    get: () => store[property],
    set: (s, val) => store[property] = val
  };
};