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
  