/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package browser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Activedd2
 */
public class Operator {

    PrintWriter p;
    JTree jTree;
    DefaultMutableTreeNode root;

    public Operator() {
        root=new DefaultMutableTreeNode("My Computer");
      //  p = new PrintWriter("out.txt");
    }

    public void getcontent() {

        File[] file = File.listRoots();
        for (File file1 : file) {
            if (file1.isDirectory()) {
                
                getcontentfiles(0, file1,root);
            }
        }
         jTree = new JTree(root);
    }

    public void getcontentfiles(int level, File file,DefaultMutableTreeNode child) {

       /* for (int i = 0; i < level; i++) {
            p.print("_");
        }*/
    //    p.println("|" + file.getAbsolutePath());
        DefaultMutableTreeNode temp=new DefaultMutableTreeNode(file.getName());
        child.add(temp);
        if (file.isFile()) {
            return;
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    getcontentfiles(level + 1, file1,temp);
                }
            }
        }

    }
    public JTree gettree(){
        return jTree;
    }
}
