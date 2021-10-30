/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link ConfigUtil} class.<br/><br/>
 * Created: 30.10.2021 10:28:58
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class ConfigUtilTest {

  private static final String CONFIG_FILE = "testConf.txt";
  private static final String NO_FILE = "___undefined.txt";

  private final String PROJECT_FOLDER = getClass().getPackageName().replace('.', '/');
  private final String PROJECT_FILE_PATH = PROJECT_FOLDER + '/' + CONFIG_FILE;
  private final String FOLDER_WITH_CONF_DIR = PROJECT_FOLDER + "/testconf";
  private final String PROJECT_WITH_CONF_DIR_FILE_PATH = FOLDER_WITH_CONF_DIR + '/' + CONFIG_FILE;

  @Test
  public void testFilePath_project_folder() throws IOException {
    String path = ConfigUtil.filePath(CONFIG_FILE, PROJECT_FOLDER);
    assertEquals(PROJECT_FILE_PATH, path);
    assertEquals("projectDir-text", IOUtil.getContentOfURI(path));
    assertNull(ConfigUtil.filePath(NO_FILE, PROJECT_FOLDER));
  }

  @Test
  public void testFilePath_project_folder_conf() throws IOException {
    String path = ConfigUtil.filePath(CONFIG_FILE, FOLDER_WITH_CONF_DIR);
    assertEquals(PROJECT_WITH_CONF_DIR_FILE_PATH, path);
    assertEquals("confDir-text", IOUtil.getContentOfURI(path));
    assertNull(ConfigUtil.filePath(NO_FILE, FOLDER_WITH_CONF_DIR));
  }

  @Test
  public void testFilePath_current_folder() throws IOException {
    // check config file in current folder
    IOUtil.writeTextFile(CONFIG_FILE, "currentDir-text");
    String path = ConfigUtil.filePath(CONFIG_FILE, ".");
    assertEquals(SystemInfo.getCurrentDir() + '/' + CONFIG_FILE, path);
    assertEquals("currentDir-text", IOUtil.getContentOfURI(path));
    deleteCurrDirConfigFile();
    assertNull(ConfigUtil.filePath(NO_FILE, FOLDER_WITH_CONF_DIR));
  }

  @Test
  public void testConfigFileMultiPath_project_folder() throws IOException {
    String path = ConfigUtil.configFilePathMulti(CONFIG_FILE, PROJECT_FOLDER);
    assertEquals("projectDir-text", IOUtil.getContentOfURI(path));
    assertEquals(PROJECT_FILE_PATH, path);
    assertNull(ConfigUtil.filePath(NO_FILE, PROJECT_FOLDER));
  }

  @Test
  public void testConfigFileMultiPath_conf_folder() throws IOException {
    String path = ConfigUtil.configFilePathMulti(CONFIG_FILE, FOLDER_WITH_CONF_DIR);
    assertEquals(PROJECT_WITH_CONF_DIR_FILE_PATH, path);
    assertEquals("confDir-text", IOUtil.getContentOfURI(path));
    assertNull(ConfigUtil.filePath(NO_FILE, FOLDER_WITH_CONF_DIR));
  }

  @Test
  public void testConfigFileMultiPath_current_folder() throws IOException {
    IOUtil.writeTextFile(CONFIG_FILE, "currentDir-text");
    String path = ConfigUtil.configFilePathMulti(CONFIG_FILE, ".");
    assertEquals(SystemInfo.getCurrentDir() + '/' + CONFIG_FILE, path);
    assertEquals("currentDir-text", IOUtil.getContentOfURI(path));
    deleteCurrDirConfigFile();
    assertNull(ConfigUtil.filePath(NO_FILE, FOLDER_WITH_CONF_DIR));
  }

  @Test
  public void testConfigFilePathDefaultLocations_project_folder() throws IOException {
    String path = ConfigUtil.configFilePathDefaultLocations(CONFIG_FILE, PROJECT_FOLDER);
    assertEquals(PROJECT_FILE_PATH, path);
    assertEquals("projectDir-text", IOUtil.getContentOfURI(path));
    assertNull(ConfigUtil.filePath(NO_FILE, PROJECT_FOLDER));
  }

  @Test
  public void testConfigFilePathDefaultLocations_project_folder_conf() throws IOException {
    String path = ConfigUtil.configFilePathDefaultLocations(CONFIG_FILE, FOLDER_WITH_CONF_DIR);
    assertEquals(PROJECT_WITH_CONF_DIR_FILE_PATH, path);
    assertEquals("confDir-text", IOUtil.getContentOfURI(path));
    assertNull(ConfigUtil.filePath(NO_FILE, FOLDER_WITH_CONF_DIR));
  }

  @Test
  public void testConfigFilePathDefaultLocations_current_folder() throws IOException {
    IOUtil.writeTextFile(CONFIG_FILE, "currentDir-text");
    String path = ConfigUtil.filePath(CONFIG_FILE, ".");
    assertEquals(SystemInfo.getCurrentDir() + '/' + CONFIG_FILE, path);
    assertEquals("currentDir-text", IOUtil.getContentOfURI(path));
    deleteCurrDirConfigFile();
    assertNull(ConfigUtil.filePath(NO_FILE, FOLDER_WITH_CONF_DIR));
  }

  private void deleteCurrDirConfigFile() {
    FileUtil.deleteIfExists(new File(CONFIG_FILE));
  }

}
