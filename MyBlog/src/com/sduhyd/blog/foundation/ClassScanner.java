package com.sduhyd.blog.foundation;

import com.sduhyd.blog.foundation.ClassFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ClassScanner {
    private static final FileFilter CLASS_FILE_FILTER  = new FileFilter() {
        @Override
        public boolean accept(File file) {
            final String filename = file.getName();
            return file.isFile() && filename.endsWith(".class") && !filename.startsWith("package-info");
        }
    };

    private static final FileFilter DIRECTORY_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }
    };
    //.class文件在计算机上的路径
    private String sourcePath;
    private Collection<ClassFilter> filters;
    public ClassScanner(String sourcePath) {
        this.sourcePath = sourcePath;
        this.filters = new LinkedList<>();
    }
    public void addFilter(ClassFilter filter) {
        if(filter != null) {
            this.filters.add(filter);
        }
    }
    /**
     *将要对packageName下的所有类进行扫描
     * @param packageName
     * @return 通过了筛选的类的集合
     */
    public Collection<Class<?>> scan(String packageName) {
        File dir = new File(this.sourcePath, packageName.replace('.', '/'));
        System.out.println("File getName:"+dir.getName()+"\nFile getPath:"+dir.getPath());
        Collection<String> classeNames = listAllClassNames(dir, packageName);
        Collection<Class<?>> classes = new LinkedList<>();
        for(String className: classeNames) {
            try {
                Class<?> clazz = Class.forName(className.replaceFirst("\\.\\.", ""));
                if(this.accept(clazz)) {
                    System.out.println("classes add:"+clazz.getName());
                    classes.add(clazz);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return classes;
    }
    private boolean accept(Class<?> clazz) {
        //只要该类有Controller注解就返回true，该类就正式缓存好，等待使用。
        for(ClassFilter filter: this.filters) {
            if(!filter.accept(clazz)) {
                return false;
            }
        }
        return true;
    }


    private Collection<String> listAllClassNames(File dir, String packageName){

        File[] classFiles = dir.listFiles(CLASS_FILE_FILTER);
        File[] directories = dir.listFiles(DIRECTORY_FILTER);

        List<String> allClassNames = new LinkedList<>();
        if(classFiles != null && classFiles.length > 0) {
            for (File classFile: classFiles) {
                System.out.println("classFile getName:"+classFile.getName());
                String simpleClassName = classFile.getName().replace(".class", "");
                allClassNames.add((packageName + '.' + simpleClassName));
            }
        }
        //如果发现子文件夹，进行递归操作找出子文件夹下的所有类的名字
        if(directories != null && directories.length > 0) {
            for(File subdir: directories) {
                allClassNames.addAll(listAllClassNames(subdir, packageName + '.' + subdir.getName()));
            }
        }
        return allClassNames;
    }
}
