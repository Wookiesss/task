import java.io.*;
import java.util.Scanner;

public class FileCopier {
    public static void main(String[] args) throws IOException {
        //创建Scanner对象
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("请输入要复制的目标路径：");
            String src1 = sc.next();
            File src = new File(src1);

            System.out.println("请输入目标文件的路径：");
            String dest1 = sc.next();
            File dest = new File(dest1);

            if (judge(src, dest)) {
                System.out.println("复制成功");
                return;
            }
        }

    }

    /*
     * 判误和判断File类型
     * @param src
     * @param dest
     * @return boolean
     * */
    public static boolean judge(File src, File dest) throws IOException {
        if (!src.exists()) {
            System.out.println("要复制的目标路径不存在");
            return false;
        }

        if (!new File(dest.getParent()).exists()) {
            System.out.println("目标文件的父级路径不存在");
            return false;
        }

        if (src.isFile() && !dest.toString().contains(".")) {
            System.out.println("目标文件的格式不规范");
            return false;
        }

        //判断文件类型
        if (src.isFile()) {
            copyFile(src, dest);
        }else{
            copyDir(src, dest);
        }
        return true;

    }

    /*
     * 文件复制
     * @param src
     * @param dest
     * */
    public static void copyFile(File src, File dest) throws IOException {
        //创建IO流对象
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(dest);
        //复制
        int len;
        byte[] bytes = new byte[1024 * 1024 * 5];
        while ((len = fis.read(bytes)) != -1) {
            fos.write(bytes, 0, len);
        }
        //释放资源
        fos.close();
        fis.close();
    }

    /*
     * 文件夹复制
     * @param src
     * @param dest
     * */
    public static void copyDir(File src, File dest) throws IOException {
        //文件夹不存在则创建
        dest.mkdirs();
        //进入文件夹
        File[] files = src.listFiles();
        //增强for遍历
        for (File file : files) {
            //判断是否为文件，调用copyFile方法
            if (file.isFile()) {
                copyFile(file, new File(dest, file.getName()));
            } else {
                //判断是否为文件夹，进行递归
                copyDir(file, new File(dest, file.getName()));
            }
        }
    }

}