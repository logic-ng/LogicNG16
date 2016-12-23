///////////////////////////////////////////////////////////////////////////
//                   __                _      _   ________               //
//                  / /   ____  ____ _(_)____/ | / / ____/               //
//                 / /   / __ \/ __ `/ / ___/  |/ / / __                 //
//                / /___/ /_/ / /_/ / / /__/ /|  / /_/ /                 //
//               /_____/\____/\__, /_/\___/_/ |_/\____/                  //
//                           /____/                                      //
//                                                                       //
//               The Next Generation Logic Library                       //
//                                                                       //
///////////////////////////////////////////////////////////////////////////
//                                                                       //
//  Copyright 2015-2016 Christoph Zengler                                //
//                                                                       //
//  Licensed under the Apache License, Version 2.0 (the "License");      //
//  you may not use this file except in compliance with the License.     //
//  You may obtain a copy of the License at                              //
//                                                                       //
//  http://www.apache.org/licenses/LICENSE-2.0                           //
//                                                                       //
//  Unless required by applicable law or agreed to in writing, software  //
//  distributed under the License is distributed on an "AS IS" BASIS,    //
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or      //
//  implied.  See the License for the specific language governing        //
//  permissions and limitations under the License.                       //
//                                                                       //
///////////////////////////////////////////////////////////////////////////

package org.logicng.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Pair}.
 * @version 1.0
 * @since 1.0
 */
public class PairTest {

  private final Pair<String, Integer> pair1 = new Pair<String, Integer>("abc", 12);
  private final Pair<String, Integer> pair2 = new Pair<String, Integer>("cde", 12);
  private final Pair<String, Integer> pair3 = new Pair<String, Integer>("cde", 42);

  @Test
  public void testGetters() {
    Assert.assertEquals("abc", pair1.first());
    Assert.assertEquals("cde", pair2.first());
    Assert.assertEquals("cde", pair3.first());
    Assert.assertEquals(12, (int) pair1.second());
    Assert.assertEquals(12, (int) pair2.second());
    Assert.assertEquals(42, (int) pair3.second());
  }

  @Test
  public void testHashCode() {
    Assert.assertEquals(pair1.hashCode(), pair1.hashCode());
    Assert.assertEquals(pair1.hashCode(), new Pair<String, Integer>("abc", 12).hashCode());
  }

  @Test
  public void testEquals() {
    Assert.assertTrue(pair1.equals(pair1));
    Assert.assertTrue(pair1.equals(new Pair<String, Integer>("abc", 12)));
    Assert.assertFalse(pair1.equals(pair2));
    Assert.assertFalse(pair2.equals(pair3));
    Assert.assertFalse(pair1.equals(pair3));
    Assert.assertFalse(pair1.equals("String"));
    Assert.assertFalse(pair1.equals(null));
  }

  @Test
  public void testToString() {
    Assert.assertEquals("<abc, 12>", pair1.toString());
    Assert.assertEquals("<cde, 12>", pair2.toString());
    Assert.assertEquals("<cde, 42>", pair3.toString());
  }

}
