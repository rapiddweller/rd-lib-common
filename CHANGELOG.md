# Release 2.1.0-jdk-11

Fixes dependency-ordering failures on complex database schemas and aligns the
build/publish pipeline with rapiddweller-benerator-ce.

- Fix: DependencyModel no longer reports "Incomplete nodes left" when a
  partially-initialized node becomes initializable mid-traversal of the
  incomplete set; postProcessNodes now repeats until that set is stable.
- Align shared dependencies with benerator: slf4j 1.7.36, log4j 2.25.4.
- Migrate Maven publishing from the retired OSSRH to the Central Portal; pin
  maven-gpg-plugin to 3.1.0; bump CI GitHub Actions off the deprecated Node 20.

# Release 2.0.1-jdk-11

Hotfix for rapiddweller-benerator-ce 3.2.1 release.

# Release 2.0.0-jdk-11

Changes are related to rapiddweller-benerator-ce 3.0.0 release check CHANGE_LOG.md for more details.

---

# Release 1.1.4-jdk-11

## Release Highlights

## Important Notes

### New features

### Changed design

### Changed implementation

## Breaking Changes

---

# Release 1.1.3-jdk-11

## Release Highlights

- Switched back to slf4j logging facade

## Important Notes

### New features

- Added method TextUtil.formatLinedTable()

- StringUtil.maxLength(String[] strings)

- MathUtil.sum(int[] addends)

- BeanUtil.isImmutable(Class)

- ArrayUtil.copyArray(Object array)

- CollectionUtil.Added method union()

### Changed design

- ThreadUtil: made isThreadSafe() and isParallelizable() public

### Changed implementation

- AnyConverter.toString()

- Improved ThreadUtil.allThreadSafe() and ThreadUtil.allParallelizable()

## Breaking Changes

- Added method forName(String className, boolean required) in the ClassProvider hierarchy

---
# Release 1.1.2-jdk-11

## Release Highlights

* Move GraalVM Converter to rapiddweller-benerator-ce module
* Introduce Checkstyle
* Remove useless unit test
* add missing doc strings

## Important Notes

N/A

## Breaking Changes

N/A
---
# Release 1.1.1-jdk-11

## Release Highlights

* Upgraded GraalVM from 20.3.0 to 21.0.0.2

## Important Notes

N/A

## Breaking Changes

N/A
  