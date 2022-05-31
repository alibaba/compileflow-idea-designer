# 打包说明

## 修改升级说明和版本升级
1. build.gradle 修改version和changeNotes
2. plugin.xml 修改version
3. README_CN.md 修改记录Changelog
4. CompileFlow.java 修改version

## 2018以下版本打包
1. 修改build.gradle的intellij配置为：
intellij {
    version '2018.2'
}

## 2020.1及以上打包
1. 修改build.gradle的intellij配置为：
intellij {
    version '2020.1'
    plugins = ['java']
}