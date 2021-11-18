/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.common.cli;

import com.rapiddweller.common.ConfigurationError;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link CommandLineParser}.<br/><br/>
 * Created: 21.10.2021 14:54:55
 * @author Volker Bergmann
 * @since 1.1.4
 */
public class CommandLineParserTest {

  private CommandLineParser p;

  @Before
  public void setUp() {
    p = new CommandLineParser();
    p.addOption("sample", "--sample", "-s");
    p.addOption("old", "--old", "-o");
    p.addOption("n", "--n", "-n");
  }

  @Test
  public void testEmpty() {
    check(false, false, SampleEnum.VAL1, OldEnum.OLD1, "default.txt");
  }

  @Test
  public void testHelp() {
    check(true, false, SampleEnum.VAL1, OldEnum.OLD1, "default.txt", "--help");
    check(true, false, SampleEnum.VAL1, OldEnum.OLD1, "default.txt", "-h");
    check(true, false, SampleEnum.VAL1, OldEnum.OLD1, "default.txt", "-help");
  }

  @Test
  public void testVersion() {
    check(false, true, SampleEnum.VAL1, OldEnum.OLD1, "default.txt", "--version");
    check(false, true, SampleEnum.VAL1, OldEnum.OLD1, "default.txt", "-v");
    check(false, true, SampleEnum.VAL1, OldEnum.OLD1, "default.txt", "-version");
  }

  @Test
  public void testSampleEnum() {
    check(false, false, SampleEnum.VAL2, OldEnum.OLD1, "default.txt", "--sample", "VAL2");
    check(false, false, SampleEnum.VAL2, OldEnum.OLD1, "default.txt", "-s", "VAL2");
  }

  @Test
  public void testOldEnum() {
    check(false, false, SampleEnum.VAL1, OldEnum.OLD2, "default.txt", "--old", "old2");
    check(false, false, SampleEnum.VAL1, OldEnum.OLD2, "default.txt", "-o", "old2");
  }

  @Test
  public void testRequiredArgument() {
    p.addArgument("file", true);
    check(false, false, SampleEnum.VAL1, OldEnum.OLD1, "specific.xml", "specific.xml");
  }

  @Test
  public void testOptionalArgument() {
    p.addArgument("file", false);
    check(false, false, SampleEnum.VAL1, OldEnum.OLD1, "specific.xml", "specific.xml");
  }

  @Test
  public void testMultipleArguments() {
    p.addArgument("file", true);
    p.addArgument("addendum", true);
    SampleConfig config = check(false, false, SampleEnum.VAL1, OldEnum.OLD1,
        "specific.xml", "specific.xml", "blabla");
    assertEquals("blabla", config.getAddendum());
  }

  @Test(expected = MissingCommandLineArgumentException.class)
  public void test2ndArgumentMissing() {
    p.addArgument("file", true);
    p.addArgument("addendum", true);
    check(false, false, SampleEnum.VAL1, OldEnum.OLD1, "specific.xml", "specific.xml");
  }

  @Test(expected = MissingCommandLineOptionValueException.class)
  public void testMissingOptionValue() {
    p.parse(new SampleConfig(), "--sample");
  }

  @Test(expected = MissingCommandLineArgumentException.class)
  public void testMissingArgument() {
    p = new CommandLineParser();
    p.addArgument("file", true);
    p.parse(new SampleConfig());
  }

  @Test(expected = IllegalCommandLineOptionException.class)
  public void testIllegalIntArgument() {
    p.parse(new SampleConfig(), "--n", "notAnInt");
  }

  // test implementation ---------------------------------------------------------------------------------------------

  private SampleConfig check(boolean expHelp, boolean expVersion, SampleEnum expEnum, OldEnum expOld, String expFile,
                    String... args) {
    SampleConfig config = new SampleConfig();
    p.parse(config, args);
    assertEquals(expHelp, config.isHelp());
    assertEquals(expVersion, config.isVersion());
    assertEquals(expEnum, config.getSample());
    assertEquals(expOld, config.getOld());
    assertEquals(expFile, config.getFile());
    return config;
  }

}
