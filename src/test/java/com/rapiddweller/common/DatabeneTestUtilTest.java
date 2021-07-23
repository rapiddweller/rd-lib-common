package com.rapiddweller.common;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class DatabeneTestUtilTest {
  @Test
  public void testIsOnline() {
    assertFalse(DatabeneTestUtil.isOnline());
  }

  @Test
  public void testFtpDownloadUrl() {
    assertNull(DatabeneTestUtil.ftpDownloadUrl());
  }

  @Test
  public void testFtpUploadUrl() {
    assertNull(DatabeneTestUtil.ftpUploadUrl());
  }
}

