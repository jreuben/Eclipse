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
package com.mobilesorcery.sdk.core;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PathExclusionFilterTest {

	private static IProject project;

	@BeforeClass
	public static void setUp() throws Exception {
		try {
			project = ResourcesPlugin.getWorkspace().getRoot().getProject("project");
			project.create(null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSimpleExclude() {
		PathExclusionFilter filter = PathExclusionFilter.parse(new String[] { "*.cpp" });
		assertFalse(filter.accept(createResource("a.cpp")));
		assertTrue(filter.accept(createResource("a.h")));
	}
	
	@Test
	public void testSeveralExcludes() {
		PathExclusionFilter filter = PathExclusionFilter.parse(new String[] { "*.cpp", "a.h" });
		assertFalse(filter.accept(createResource("a.cpp")));
		assertFalse(filter.accept(createResource("a.h")));
	}
	
	@Test
	public void testExplicitInclude() {
		PathExclusionFilter filter = PathExclusionFilter.parse(new String[] { "*.cpp", "a.h", "+b.cpp" });
		assertFalse(filter.accept(createResource("a.cpp")));
		assertFalse(filter.accept(createResource("a.h")));
		assertTrue(filter.accept(createResource("b.cpp")));
	}

	@Test
	public void testDuplicates() {
		PathExclusionFilter filter = PathExclusionFilter.parse(new String[] { "a.h", "a.h" });
		assertEquals(1, filter.getFileSpecs().length);
		assertEquals("a.h", filter.getFileSpecs()[0]);
	}
	
	@Test
	public void testCancellation() {
		PathExclusionFilter filter = PathExclusionFilter.parse(new String[] { "-a.h", "+a.h" });
		assertEquals(0, filter.getFileSpecs().length);
	}
	
	private IResource createResource(String path) {
		return project.getFile(path);
	}
	
}
