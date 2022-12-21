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
package com.itsaky.androidide.viewmodel

import androidx.core.util.Pair
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import java.io.File

/** ViewModel for data used in [com.itsaky.androidide.EditorActivityKt] */
class EditorViewModel : ViewModel() {
  
  @JvmField val progressBarVisible = MutableLiveData(false)
  @JvmField val isInitializing = MutableLiveData(false)

  internal val _statusText = MutableLiveData<CharSequence>("")
  internal val _displayedFile = MutableLiveData(-1)
  internal val _fileTreeDrawerOpened = MutableLiveData(false)
  private val files = MutableLiveData<MutableList<File>>(ArrayList())
  private val fileModified = MutableLiveData(false)

  /**
   * Holds information about the currently selected editor fragment. First value in the pair is the
   * index of the editor opened. Second value is the file that is opened.
   */
  private val mCurrentFile = MutableLiveData<Pair<Int, File?>?>(null)
  
  var statusText: CharSequence
    get() = this._statusText.value ?: ""
    set(value) {
      _statusText.value = value
    }
  
  var displayedFileIndex: Int
    get() = _displayedFile.value!!
    set(value) {
      _displayedFile.value = value
    }
  
  var fileTreeDrawerOpened : Boolean
    get() = _fileTreeDrawerOpened.value ?: false
    set(value) {
      _fileTreeDrawerOpened.value = value
    }

  /**
   * Add the given file to the list of opened files.
   *
   * @param file The file that has been opened.
   */
  fun addFile(file: File) {
    val files = files.value ?: mutableListOf()
    files.add(file)
    this.files.value = files
  }

  /**
   * Remove the file at the given index from the list of opened files.
   *
   * @param index The index of the closed file.
   */
  fun removeFile(index: Int) {
    val files = files.value ?: mutableListOf()
    files.removeAt(index)
    this.files.value = files
  }

  fun removeAllFiles() {
    files.value = mutableListOf()
    setCurrentFile(-1, null)
  }

  fun setCurrentFile(index: Int, file: File?) {
    displayedFileIndex = index
    mCurrentFile.value = Pair.create(index, file)
  }

  /**
   * Get the opened file at the given index.
   *
   * @param index The index of the file.
   * @return The file at the given index.
   */
  fun getOpenedFile(index: Int): File {
    return files.value!![index]
  }

  /**
   * Get the number of files opened.
   *
   * @return The number of files opened.
   */
  fun getOpenedFileCount(): Int {
    return files.value!!.size
  }

  /**
   * Get the list of currently opened files.
   *
   * @return The list of opened files.
   */
  fun getOpenedFiles(): List<File> {
    return files.value!!
  }

  /**
   * Add an observer to the list of opened files.
   *
   * @param lifecycleOwner The lifecycle owner.
   * @param observer The observer.
   */
  fun observeFiles(lifecycleOwner: LifecycleOwner?, observer: Observer<MutableList<File>?>?) {
    files.observe(lifecycleOwner!!, observer!!)
  }

  fun getCurrentFileIndex(): Int {
    return mCurrentFile.value?.first ?: -1
  }

  fun getCurrentFile(): File? {
    return mCurrentFile.value?.second
  }

  fun setFilesModified(modified: Boolean) {
    fileModified.value = modified
  }

  fun areFilesModified(): Boolean {
    val modified = fileModified.value
    return modified != null && modified
  }
}