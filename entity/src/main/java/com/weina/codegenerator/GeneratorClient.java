package com.weina.codegenerator;

import org.mybatis.generator.api.ShellRunner;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/13 11:03
 */
public class GeneratorClient {
    public static void main(String[] args) {
        String path = GeneratorClient.class.getResource("/").getPath();
        String filePath = path + "generatorConfig.xml";
        args = new String[] { "-configfile", filePath, "-overwrite" };
        //E:\Workspaces\IdeaProjects\myproject\entity\src\main\resources\generatorConfig.xml
        ShellRunner.main(args);
    }

}
