# mybatis逆向工程

## 导包

```java
            <!--mybatis-generator-->
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <configuration>
                    <!--配置文件路径，可以写绝对路径，也可以写相对路径-->

                    <configurationFile>
                		<!-- TODO 1. -->
                        src/main/resources/mybatis-generator/generatorConfig.xml
                    </configurationFile>
                    <overwrite>true</overwrite>
                    <verbose>true</verbose>
                </configuration>
                <dependencies>
                    <!--数据库驱动-->
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>8.0.20</version>
                    </dependency>
                    <!--非官方插件引入-->
                    <dependency>
                        <groupId>com.itfsw</groupId>
                        <artifactId>mybatis-generator-plugin</artifactId>
                        <version>1.3.8</version>
                    </dependency>
                    <!--swagger+mybatis-->
                    <dependency>
                        <groupId>com.spring4all</groupId>
                        <artifactId>swagger-spring-boot-starter</artifactId>
                        <version>1.9.0.RELEASE</version>
                    </dependency>
                    <dependency>
                        <groupId>com.github.misterchangray.mybatis.generator.plugins</groupId>
                        <artifactId>myBatisGeneratorPlugins</artifactId>
                        <version>1.2</version>
                    </dependency>
                </dependencies>
            </plugin>
```
## 生成配置

generatorConfig.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <!--
        defaultModelType:指定生成对象的样式
            1，conditional：类似hierarchical；
            2，flat：所有内容（主键，blob）等全部生成在一个对象中；
            3，hierarchical：主键生成一个XXKey对象(key class)，Blob等单独生成一个对象，其他简单属性在一个对象中(record class)
    -->
    <context id="mysqlgenerator" targetRuntime="MyBatis3" defaultModelType="flat">
        <property name="autoDelimitKeywords" value="true"/>
        <!--可以使用``包括字段名，避免字段名与sql保留字冲突报错-->
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>

        <!-- 自动生成toString方法 -->
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin"/>
        <!-- 自动生成equals方法和hashcode方法 -->
        <plugin type="org.mybatis.generator.plugins.EqualsHashCodePlugin"/>
        <!--生成mapper.xml时覆盖原文件-->
        <plugin type="org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin"/>
        <!--
            非官方插件 https://github.com/itfsw/mybatis-generator-plugin
            如果使用java代码方式生成，代码，请注释掉此部分配置，仅支持maven插件使用
        -->
        <!-- 查询单条数据插件 -->
        <plugin type="com.itfsw.mybatis.generator.plugins.SelectOneByExamplePlugin"/>
        <!-- 查询结果选择性返回插件 -->
        <plugin type="com.itfsw.mybatis.generator.plugins.SelectSelectivePlugin"/>
        <!-- Example Criteria 增强插件 -->
        <plugin type="com.itfsw.mybatis.generator.plugins.ExampleEnhancedPlugin"/>
        <!-- 数据Model属性对应Column获取插件 -->
        <plugin type="com.itfsw.mybatis.generator.plugins.ModelColumnPlugin"/>
        <!-- 逻辑删除插件 -->
        <plugin type="com.itfsw.mybatis.generator.plugins.LogicalDeletePlugin">
            <!-- 这里配置的是全局逻辑删除列和逻辑删除值，当然在table中配置的值会覆盖该全局配置 -->
            <!-- 逻辑删除列类型只能为数字、字符串或者布尔类型 -->
            <property name="logicalDeleteColumn" value="deleted"/>
            <property name="logicalDeleteColumn" value="is_del"/>
            <!-- 逻辑删除-已删除值 -->
            <property name="logicalDeleteValue" value="1"/>
            <!-- 逻辑删除-未删除值 -->
            <property name="logicalUnDeleteValue" value="0"/>
        </plugin>
        <!-- 批量插入插件 -->
        <plugin type="com.itfsw.mybatis.generator.plugins.BatchInsertPlugin">
            <!--
            开启后可以实现官方插件根据属性是否为空决定是否插入该字段功能
            ！需开启allowMultiQueries=true多条sql提交操作，所以不建议使用！插件默认不开启
            -->
            <property name="allowMultiQueries" value="false"/>
        </plugin>
        <!-- 表重命名配置插件 -->
        <!--<plugin type="com.itfsw.mybatis.generator.plugins.TableRenameConfigurationPlugin">
            &lt;!&ndash; Mapper -> Dao, Mapper.xml -> AutoMapper.xml &ndash;&gt;
            &lt;!&ndash;<property name="clientSuffix" value="AutoMapper"/>
            &lt;!&ndash; Exmaple -> Query &ndash;&gt;&ndash;&gt;
            &lt;!&ndash;<property name="exampleSuffix" value="AutoExample"/>&ndash;&gt;
            &lt;!&ndash; tb -> TbAutoEntity &ndash;&gt;
            &lt;!&ndash;<property name="modelSuffix" value="AutoEntity"/>&ndash;&gt;
        </plugin>-->
        
       <!-- mybatis 与swagger 生成的实体类注释-->
        <plugin type="mybatis.generator.plugins.GeneratorSwagger2Doc">
            <property name="apiModelAnnotationPackage" value="io.swagger.annotations.ApiModel"/>
            <property name="apiModelPropertyAnnotationPackage" value="io.swagger.annotations.ApiModelProperty"/>
        </plugin>

        <commentGenerator>
            <!--是否去除默认生成的注释-->
            <property name="suppressAllComments" value="true"/>
            <!--是否去除包含时间戳的注释-->
            <property name="suppressDate" value="true"/>
        </commentGenerator>

        <!--TODO 1.-->
        <!--数据库连接信息-->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/user?useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=UTC"
                        userId="root"
                        password="123456"/>

        <!--
            java类型处理器
            用于处理DB中的类型到Java中的类型，默认使用JavaTypeResolverDefaultImpl；
            注意一点，默认会先尝试使用Integer，Long，Short等来对应DECIMAL和 NUMERIC数据类型；
        -->
        <javaTypeResolver>
            <!--
                useJSR310Types
                主要用于java8之后新日期类型支持：LocalDateTime
            -->
            <property name="useJSR310Types" value="true"/>
            <!--
                true：使用BigDecimal对应DECIMAL和 NUMERIC数据类型
                false：默认
                    scale>0;length>18：使用BigDecimal;
                    scale=0;length[10,18]：使用Long；
                    scale=0;length[5,9]：使用Integer；
                    scale=0;length<5：使用Short；
            -->
            <property name="forceBigDecimals" value="true"/>
        </javaTypeResolver>

        <!--TODO 2.-->
        <javaModelGenerator targetPackage="com.xiaoruiit.project.demo.domain.auto" targetProject="src/main/java"/>
        <!--mapper.xml 文件生成目录-->
        <!--TODO 3.-->
        <sqlMapGenerator targetPackage="auto" targetProject="src/main/resources/mapper/user"/>
        <!--mapper接口生成目录-->
        <!--TODO 4.-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.xiaoruiit.project.demo.mapper.auto" targetProject="src/main/java"/>

        <!--表-->
        <!--<table tableName="user_info">
            &lt;!&ndash;generatedKey用于生成生成主键的方法&ndash;&gt;
            <generatedKey column="id" sqlStatement="MySql" identity="true"/>
            &lt;!&ndash;重写字段数据类型&ndash;&gt;
            <columnOverride column="status" jdbcType="TINYINT" javaType="java.lang.Boolean"/>
        </table>
        <table tableName="user_detail_info">
            &lt;!&ndash;generatedKey用于生成生成主键的方法&ndash;&gt;
            <generatedKey column="id" sqlStatement="MySql" identity="true"/>
            &lt;!&ndash;重写字段数据类型&ndash;&gt;
            <columnOverride column="is_regular" jdbcType="TINYINT" javaType="java.lang.Boolean"/>
            <columnOverride column="is_probation" jdbcType="TINYINT" javaType="java.lang.Boolean"/>
        </table>-->
        <!--TODO 5.-->
        <table tableName = "goods_price">
            <generatedKey column="id" sqlStatement="MySql" identity="true"/>
        </table>
        <table tableName = "material_price">
            <generatedKey column="id" sqlStatement="MySql" identity="true"/>
        </table>
    </context>
</generatorConfiguration>

```



## 使用

![image-20221002101715543](https://xiaoruiit.oss-cn-beijing.aliyuncs.com/img/image-20221002101715543.png)
