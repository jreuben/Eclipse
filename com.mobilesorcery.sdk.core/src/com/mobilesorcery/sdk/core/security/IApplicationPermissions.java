/*  Copyright (C) 2009 Mobile Sorcery AB

    This program is free software; you can redistribute it and/or modify it
    under the terms of the Eclipse Public License v1.0.

    This program is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License v1.0 for
    more details.

    You should have received a copy of the Eclipse Public License v1.0 along
    with this program. It is also available at http://www.eclipse.org/legal/epl-v10.html
*/
package com.mobilesorcery.sdk.core.security;

import java.util.List;

public interface IApplicationPermissions {

    public List<String> getRequiredPermissions();
    
    public void setRequiredPermissions(List<String> required);
    
    public void setRequiredPermission(String required, boolean set);
    
    public boolean isPermissionRequired(String key);
    
    public List<String> getAvailablePermissions(String parentPermission);

    public IApplicationPermissions createWorkingCopy();
    
    public void apply(IApplicationPermissions workingCopy);
}
