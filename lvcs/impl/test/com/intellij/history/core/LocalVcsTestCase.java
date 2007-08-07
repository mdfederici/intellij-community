package com.intellij.history.core;

import com.intellij.history.Clock;
import com.intellij.history.core.changes.*;
import com.intellij.history.core.storage.ByteContent;
import com.intellij.history.core.storage.Content;
import com.intellij.history.core.tree.Entry;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public abstract class LocalVcsTestCase extends Assert {
  private Locale myDefaultLocale;
  private int myCurrentEntryId = 1;

  @Before
  public void setUpLocale() {
    myDefaultLocale = Locale.getDefault();
    Locale.setDefault(new Locale("ru", "RU"));
  }

  @After
  public void restoreLocale() {
    Locale.setDefault(myDefaultLocale);
  }

  protected int getNextId() {
    return myCurrentEntryId++;
  }

  protected static byte[] b(String s) {
    return s.getBytes();
  }

  protected static Content c(String data) {
    return new ByteContent(b(data));
  }

  public static ContentFactory cf(String data) {
    return createContentFactory(b(data));
  }

  public static ContentFactory bigContentFactory() {
    return createContentFactory(new byte[ContentFactory.MAX_CONTENT_LENGTH + 1]);
  }

  private static ContentFactory createContentFactory(final byte[] bytes) {
    return new ContentFactory() {
      @Override
      public byte[] getBytes() {
        return bytes;
      }

      @Override
      public long getLength() {
        return bytes.length;
      }
    };
  }

  public static <T> T[] array(T... objects) {
    return objects;
  }

  public static <T> List<T> list(T... objects) {
    return Arrays.asList(objects);
  }

  protected static IdPath idp(int... parts) {
    return new IdPath(parts);
  }

  protected static ChangeSet cs(Change... changes) {
    return cs(null, changes);
  }

  protected static ChangeSet cs(String name, Change... changes) {
    return cs(0, name, changes);
  }

  protected static ChangeSet cs(long timestamp, Change... changes) {
    return cs(timestamp, null, changes);
  }

  protected static ChangeSet cs(long timestamp, String name, Change... changes) {
    return new ChangeSet(timestamp, name, Arrays.asList(changes));
  }

  protected static void createFile(Entry r, int id, String path, Content c, long timestamp) {
    new CreateFileChange(id, path, c, timestamp).applyTo(r);
  }

  protected void createFile(Entry r, String path, Content c, long timestamp) {
    createFile(r, getNextId(), path, c, timestamp);
  }

  protected static void createDirectory(Entry r, int id, String path) {
    new CreateDirectoryChange(id, path).applyTo(r);
  }

  protected void createDirectory(Entry r, String path) {
    createDirectory(r, getNextId(), path);
  }

  protected static void changeFileContent(Entry r, String path, Content c, long timestamp) {
    new ChangeFileContentChange(path, c, timestamp).applyTo(r);
  }

  protected static void rename(Entry r, String path, String newName) {
    new RenameChange(path, newName).applyTo(r);
  }

  protected static void move(Entry r, String path, String newParent) {
    new MoveChange(path, newParent).applyTo(r);
  }

  protected static void delete(Entry r, String path) {
    new DeleteChange(path).applyTo(r);
  }

  protected static void setCurrentTimestamp(long t) {
    Clock.setCurrentTimestamp(t);
  }

  protected static void assertEquals(Object[] expected, Collection actual) {
    assertArrayEquals(expected, actual.toArray());
  }
}
