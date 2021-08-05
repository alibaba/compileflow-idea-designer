/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.compileflow.idea.graph.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.intellij.openapi.diagnostic.Logger;

/**
 * @author xuan
 * @since 2020/11/6
 */
public class LocalKvUtil {
    private static final Logger log = Logger.getInstance(LocalKvUtil.class);

    private static final Map<String, String> CACHE = new HashMap<>();

    public synchronized static void put(String path, String key, String value) {
        CACHE.put(buildKey(path, key), value);
        try {
            Properties properties = getPropertiesFromPath(path);
            if (null == properties) {
                return;
            }

            properties.put(key, value);
            writeProperties(path, properties);
        } catch (Exception e) {
            //Ignore
        }
    }

    //public synchronized static void put(String path, Map<String, String> map) {
    //    if (null == map) {
    //        return;
    //    }
    //
    //    Properties properties = getPropertiesFromPath(path);
    //    if (null == properties) {
    //        return;
    //    }
    //
    //    properties.putAll(map);
    //    writeProperties(path, properties);
    //}

    private static String get(String path, String key) {

        String value = CACHE.get(buildKey(path, key));
        if (isNotEmpty(value)) {
            return value;
        }

        try {
            Properties properties = getPropertiesFromPath(path);
            if (null == properties) {
                return null;
            }

            String valueFromFile = properties.getProperty(key);
            if (isNotEmpty(valueFromFile)) {
                CACHE.put(buildKey(path, key), valueFromFile);
            }
            return valueFromFile;
        } catch (Exception e) {
            //Ignore
        }
        return null;
    }

    public static String get(String path, String key, String defaultStr) {
        String value = get(path, key);
        if (null != value) {
            return value;
        }
        return defaultStr;
    }

    //public synchronized static void remove(String path, String key) {
    //    Properties properties = getPropertiesFromPath(path);
    //    if (null == properties) {
    //        return;
    //    }
    //
    //    properties.remove(key);
    //    writeProperties(path, properties);
    //}

    //public synchronized static void removeAll(String path) {
    //    if (null == path) {
    //        return;
    //    }
    //
    //    File file = new File(path);
    //    if (file.exists()) {
    //        boolean b = file.delete();
    //        if (!b) {
    //            log.error("file.delete() fail. path=" + path);
    //        }
    //    }
    //}

    private static Properties getPropertiesFromPath(String path) {
        initFileForPath(path);
        return readProperties(path);
    }

    private static void initFileForPath(String path) {
        if (null == path) {
            return;
        }
        try {
            File file = new File(path);
            File parentFile = file.getParentFile();

            if (!parentFile.exists()) {
                boolean b = parentFile.mkdirs();
                if (!b) {
                    throw new Exception("parentFile.mkdirs() fail.");
                }
            }

            if (!file.exists()) {
                boolean b = file.createNewFile();
                if (!b) {
                    throw new Exception("file.createNewFile() fail.");
                }
            }
        } catch (Exception e) {
            log.error(String.format("createNewFile fail. Properties[%s]", path), e);
        }
    }

    private static Properties readProperties(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        Properties properties = new Properties();
        InputStream in = null;

        try {
            in = new FileInputStream(file);
            properties.load(in);
        } catch (IOException e) {
            log.error(String.format("Could not read Properties[%s]", path), e);
        } finally {
            closeQuietly(in);
        }

        return properties;
    }

    private static void writeProperties(String path, Properties properties) {
        OutputStream out = null;

        try {
            out = new FileOutputStream(path);
            properties.store(out, null);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not write Properties[%s]", path), e);
        } finally {
            closeQuietly(out);
        }
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            if (null != closeable) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    private static String buildKey(String path, String key) {
        return path + "_" + key;
    }

    private static boolean isNotEmpty(String str) {
        return null != str && str.trim().length() > 0;
    }

}
