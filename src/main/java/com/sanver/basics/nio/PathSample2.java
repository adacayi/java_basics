package com.sanver.basics.nio;

import java.nio.file.Path;

public class PathSample2 {
    public static void main(String[] args) {
        var path1 = Path.of("\\folder1\\folder2\\some.txt"); // This is an absolute path, but isAbsolute will return false for Windows systems.
        var path2 = Path.of("\\folder3\\one.txt"); // This is an absolute path, but isAbsolute will return false for Windows systems.
        var path3 = Path.of("C:\\folder4\\other.txt"); // This is an absolute path, and isAbsolute will return true for Windows systems.
        var path4 = Path.of("folder5\\other2.txt"); // This is a relative path
        var path5 = Path.of("folder5\\folder6\\other3.txt"); // This is a relative path
        var path6 = Path.of("\\folder1\\folder2\\.\\folder3\\..\\some2.txt"); // When resolved this equals to \\folder1\folder2\some2.txt, since `.` means current directory and `..` means parent directory.
        var path7 = Path.of("folder1\\folder2\\.\\folder3\\..\\some3.txt");
        var path8 = Path.of("folder1\\folder2\\.\\folder3\\folder4\\..\\some4.txt");
        var format = "%-38s : %s%n";
        System.out.printf(format, "path1", path1);
        System.out.printf(format, "path2", path2);
        System.out.printf(format, "path3", path3);
        System.out.printf(format, "path4", path4);
        System.out.printf(format, "path5", path5);
        System.out.printf(format, "path6", path6);
        System.out.printf(format, "path6.normalize()", path6.normalize());
        System.out.printf(format, "path7", path7);
        System.out.printf(format, "path7.normalize()", path7.normalize());
        System.out.printf(format, "path8", path8);
        System.out.printf(format, "path8.normalize()", path8.normalize());

        System.out.println();

        System.out.printf(format, "path4.resolve(path5)", path4.resolve(path5));
        System.out.printf(format, "path5.resolve(path4)", path5.resolve(path4));
        System.out.printf(format, "path4.resolve(path1)", path4.resolve(path1));
        System.out.printf(format, "path4.resolve(path3)", path4.resolve(path3));
        System.out.printf(format, "path1.resolve(path4)", path1.resolve(path4));
        System.out.printf(format, "path3.resolve(path4)", path3.resolve(path4));
        System.out.printf(format, "path6.resolve(path4)", path6.resolve(path4));
        System.out.printf(format, "path6.resolve(path6.normalize())", path6.resolve(path6.normalize()));
        System.out.printf(format, "path6.normalize().resolve(path6)", path6.normalize().resolve(path6));
        System.out.printf(format, "path7.resolve(path7.normalize())", path7.resolve(path7.normalize()));
        System.out.printf(format, "path7.normalize().resolve(path7)", path7.normalize().resolve(path7));
        System.out.printf(format, "path7.resolve(path8)", path7.resolve(path8));
        System.out.printf(format, "path7.relativize(path7.resolve(path8))", path7.relativize(path7.resolve(path8))); // This is equal to path8.normalize(), because relativize() normalizes the paths before relativizing
        System.out.println();

        System.out.printf(format, "path1.resolve(path2)", path1.resolve(path2));
        System.out.printf(format, "path2.resolve(path1)", path2.resolve(path1));
        System.out.printf(format, "path1.resolve(path3)", path1.resolve(path3));
        System.out.printf(format, "path3.resolve(path1)", path3.resolve(path1));

        System.out.println();
        System.out.printf(format, "path1.relativize(path2)", path1.relativize(path2));
        System.out.printf(format, "path2.relativize(path1)", path2.relativize(path1));
//        System.out.printf(format, "path1.relativize(path4)", path1.relativize(path4)); // this would result in an exception, since path1 is absolute while path4 is relative
//        System.out.printf(format, "path1.relativize(path3)", path1.relativize(path3)); // this would result in an exception, although both path1 and path3 is absolute, path1.isAbsolute results in false
        System.out.printf(format, "path1.relativize(path6)", path1.relativize(path6));
        System.out.printf(format, "path6.relativize(path1)", path6.relativize(path1));
        System.out.printf(format, "path6.relativize(path1).getNameCount()", path6.relativize(path1).getNameCount());
        System.out.printf(format, "path3.resolve(path1).relativize(path3)", path3.resolve(path1).relativize(path3));
        System.out.printf(format, "path3.resolve(path1).relativize(path3).isAbsolute()", path3.resolve(path1).relativize(path3).isAbsolute());
        System.out.printf(format, "path6.relativize(path6.normalize())", path6.relativize(path6.normalize()));
        System.out.printf(format, "path6.normalize().relativize(path6)", path6.normalize().relativize(path6));
        System.out.printf(format, "path6.relativize(path6).getNameCount()", path6.relativize(path6).getNameCount());
        System.out.printf(format, "path6.relativize(path6).getName(0)", path6.relativize(path6).getName(0));
    }
}
