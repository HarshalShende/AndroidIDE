/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.itsaky.androidide.uidesigner.undo

import com.itsaky.androidide.inflater.IView
import com.itsaky.androidide.inflater.IViewGroup
import kotlin.math.max
import kotlin.math.min

/**
 * Represents the action when the user removes a view from a view group.
 *
 * @author Akash Yadav
 */
class ViewRemovedAction(child: IView, parent: IViewGroup, index: Int) :
  ViewAction(child, parent, index) {

  override fun undo() {
    parent.addChild(parent.validateIndex(index), child)
  }

  override fun redo() {
    child.removeFromParent()
  }
}
