/*
 * Copyright (c) 2017 Dragan Zuvic
 *
 * This file is part of jtsgen.
 *
 * jtsgen is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jtsgen is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jtsgen.  If not, see http://www.gnu.org/licenses/
 *
 */

package dz.jtsgen.processor.helper;

import org.junit.Test;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;

import static dz.jtsgen.processor.helper.ExecutableElementHelper.isGetter;
import static dz.jtsgen.processor.helper.ExecutableElementHelper.isSetter;
import static dz.jtsgen.processor.helper.ExecutableElementHelper.nameFromMethod;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExecutableElementHelperTest {
    @Test
    public void isGetterOrSetter() throws Exception {
        assertFalse(ExecutableElementHelper.isGetterOrSetter((String) null));
        assertFalse(ExecutableElementHelper.isGetterOrSetter((Element) null));
        assertFalse(ExecutableElementHelper.isGetterOrSetter(createDummy("set")));
        assertFalse(ExecutableElementHelper.isGetterOrSetter(createDummy("get")));
        assertFalse(ExecutableElementHelper.isGetterOrSetter(createDummy("bla")));
        assertTrue(ExecutableElementHelper.isGetterOrSetter(createDummy("setA")));
        assertTrue(ExecutableElementHelper.isGetterOrSetter(createDummy("getA")));
    }

    @Test
    public void isSetter_tests() throws Exception {
        assertFalse(isSetter(null));
        assertTrue(isSetter(createDummy("setA")));
        assertFalse(isSetter(createDummy("set")));
        assertFalse(isSetter(createDummy(null)));
        assertFalse(isSetter(createDummySimpleNameIsNull()));
    }

    @Test
    public void isGetter_tests() throws Exception {
        assertFalse(isGetter((Element) null));
        assertTrue(isGetter(createDummy("getA")));
        assertFalse(isGetter(createDummy(null)));
        assertFalse(isGetter(createDummySimpleNameIsNull()));
        assertFalse(isGetter(createDummy("get")));
    }

    private ExecutableElement createDummy(final String simpleName) {
        final ExecutableElement mock = mock(ExecutableElement.class);
        final Name nameMock = mock(Name.class);
        when(nameMock.toString()).thenReturn(simpleName);
        when(mock.getSimpleName()).thenReturn(nameMock);
        return mock;
    }

    private ExecutableElement createDummySimpleNameIsNull() {
        final ExecutableElement mock = mock(ExecutableElement.class);
        when(mock.getSimpleName()).thenReturn(null);
        return mock;
    }

    @Test
    public void extract_methodName_fromGetterSetter() throws Exception {
        assertEquals("x", nameFromMethod("setX"));
        assertEquals("x", nameFromMethod("getX"));
        assertEquals("x_b", nameFromMethod("getX_b"));
        assertEquals("x_b", nameFromMethod("setX_b"));
        assertEquals("x_with_getter_setter", nameFromMethod("setX_with_getter_setter"));
    }

}