
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2012, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package org.carrot2.util.attribute.constraint;

import java.io.File;
import java.lang.annotation.Annotation;

class IsDirectoryConstraint extends IsFileConstraintBase
{
    @Override
    boolean isFileConstraintMet(File file)
    {
        return file.isDirectory();
    }

    @Override
    protected void populateCustom(Annotation annotation)
    {
        this.mustExist = ((IsDirectory) annotation).mustExist();
    }
}