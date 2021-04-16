import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Vector;

public class MainClass
{
    static class info {
        static int infonum;
    }
    static class sedit {
        static int len;
        static String[] words = new String[2048];
        static int editnum;
        static String[] snum = new String[2048];
        //static Vector<Vector<String>> vec = null;
    }

    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            boolean v = file.delete();
            System.out.println(v);
        }
    }

    public static void write(String path, String str) {
        FileWriter fw = null;
        try {
            File f = new File(path);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert fw != null;
        PrintWriter pw = new PrintWriter(fw);
        pw.print(str);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String read(String file) throws IOException{

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedOut = new BufferedReader(fileReader);
        String line = bufferedOut.readLine();
        StringBuilder str = new StringBuilder(line);
        while(line != null) {
            str.append("\n");
            //System.out.println(line);
            line = bufferedOut.readLine();
            if(line != null) {
                str.append(line).append("\n");
            }
        }
        return str.toString();
    }

    public static Vector<Vector<String>> ReadFile(String path) throws IOException {
        File fp = new File(path);
        boolean s;

        // 文件是否存在
        if (!fp.exists()) {
            s = fp.createNewFile();
            if (!s) {
                System.out.println("File cannot be read!!");
            }
            return new Vector<>();
        } else {
            Vector<Vector<String>> v = new Vector<>();
            BufferedReader r = new BufferedReader(new FileReader(fp));

            while (r.ready()) {
                String l = r.readLine();
                if (!l.isEmpty()) {
                    Scanner sn = new Scanner(l);
                    Vector<String> tmp = new Vector<>();
                    //No, Name, Salary, Date, Email
                    tmp.add(sn.next());
                    tmp.add(sn.next());
                    tmp.add(sn.next());
                    tmp.add(sn.next());
                    tmp.add(sn.next());
                    v.add(tmp);
                }
            }
            r.close();
            return v;
        }
    }
    
    public static void main(String[] agrs)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        System.out.println(formatter.format(calendar.getTime()));

        JFrame frame = new JFrame("职工信息管理系统");
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel done = new JPanel();
        JPanel addinfo = new JPanel();
        JPanel editinfo = new JPanel();
        JPanel insert = new JPanel();
        JPanel cards = new JPanel(new CardLayout());
        CardLayout cl = (CardLayout)(cards.getLayout());

        final int[] i = {0};

        JTextField infonum = new JTextField("请输入要添加的人数",20);
        addinfo.add(infonum);
        JButton adddone = new JButton("确定");
        addinfo.add(adddone);
        adddone.addActionListener(e -> {
            info.infonum = Integer.parseInt(infonum.getText().trim());
            System.out.println(info.infonum);
            cl.show(cards,"card2");
        });
        cards.add(addinfo,"addinfo");

        JTextField num = new JTextField("职工工号",20);
        JTextField name = new JTextField("姓名",20);
        JTextField birth = new JTextField("出生年月",20);
        JTextField money = new JTextField("基本工资",20);
        JTextField email = new JTextField("email",20);
        JButton push = new JButton("提交");
        p2.add(num);
        p2.add(name);
        p2.add(birth);
        p2.add(money);
        p2.add(email);
        p2.add(push);

        push.addActionListener(e -> {

            write("out.txt", formatter.format(calendar.getTime()) + num.getText().trim());
            write("out.txt", " ");
            write("out.txt", name.getText().trim());
            write("out.txt", " ");
            write("out.txt", birth.getText().trim());
            write("out.txt", " ");
            write("out.txt", money.getText().trim());
            write("out.txt", " ");
            write("out.txt", email.getText().trim());
            write("out.txt", "\n");

            System.out.println(i[0]);
            i[0]++;
            JOptionPane.showMessageDialog(p2 ,"完成", "完成",JOptionPane.WARNING_MESSAGE);
            if(i[0]>=info.infonum){
                cl.show(cards,"done");
            }
        });
        cards.add(p2,"card2");

        //JTextField inum = new JTextField(sedit.snum[sedit.editnum-1],20);
        JTextField iname = new JTextField("姓名",20);
        JTextField ibirth = new JTextField("出生年月",20);
        JTextField imoney = new JTextField("基本工资",20);
        JTextField iemail = new JTextField("email",20);
        JButton ipush = new JButton("提交");
        //insert.add(inum);
        insert.add(iname);
        insert.add(ibirth);
        insert.add(imoney);
        insert.add(iemail);
        insert.add(ipush);

        ipush.addActionListener(e -> {

            write("a.txt", sedit.snum[sedit.editnum-1]);
            write("a.txt", " ");
            write("a.txt", iname.getText().trim());
            write("a.txt", " ");
            write("a.txt", ibirth.getText().trim());
            write("a.txt", " ");
            write("a.txt", imoney.getText().trim());
            write("a.txt", " ");
            write("a.txt", iemail.getText().trim());
            write("a.txt", "\n");
            deleteFile("out.txt");
            File file1=new File("a.txt");
            File file2=new File("out.txt");
            boolean flag = file1.renameTo(file2);
            System.out.println(flag);
            JOptionPane.showMessageDialog(p2 ,"完成", "完成",JOptionPane.WARNING_MESSAGE);
            cl.show(cards,"done");
        });
        cards.add(insert,"insert");

        JTextField editinfo1 = new JTextField("请输入要修改的人的序号",20);
        editinfo.add(editinfo1);
        JButton editdone = new JButton("确定");
        editinfo.add(editdone);
        editdone.addActionListener(e -> {
            sedit.editnum = Integer.parseInt(editinfo1.getText().trim());
            System.out.println(sedit.editnum);
            for(int xe=0; xe<sedit.len; xe++) {
                if(xe != sedit.editnum-1) {
                    write("a.txt",sedit.words[xe]);
                    write("a.txt","\n");
                }
            }
            cl.show(cards,"insert");

        });
        cards.add(editinfo,"editinfo");

        done.add(new JTextArea("完成"));
        JButton back = new JButton("返回");
        done.add(back);
        back.addActionListener(e -> {
            i[0]=0;
            cl.show(cards,"card1");
        });
        cards.add(done,"done");

        JButton b = new JButton("添加职工信息");
        p1.add(b);
        b.addActionListener(e -> cl.show(cards,"addinfo"));

        JButton show = new JButton("显示职工信息");
        p1.add(show);
        show.addActionListener(e -> {
            String allinfo = "职工工号 姓名 出生年月 基本工资 email\n";
            try {
                allinfo = allinfo + read("out.txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            JOptionPane.showMessageDialog(p2 ,allinfo, "完成",JOptionPane.WARNING_MESSAGE);
        });

        JButton edit = new JButton("员工信息修改");
        p1.add(edit);
        edit.addActionListener(e -> {

            Vector<Vector<String>> vec = null;
            try {
                vec = ReadFile("out.txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            assert vec != null;
            int len = vec.size();
            sedit.len = len;
            for(int ye=0;ye<sedit.len;ye++) {
                sedit.snum[ye] = vec.get(ye).get(0);
            }
            System.out.println(Arrays.toString(sedit.snum));
            int m = 0;
            try {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("out.txt")));
                String linestr;
                while ((linestr = br.readLine()) != null) {
                    sedit.words[m++] = linestr;
                }
                br.close();
            } catch (IOException ioException) {
                System.out.println("文件操作失败");
                ioException.printStackTrace();
            }
            StringBuilder infoout = new StringBuilder("1");
            for(int uni=1; uni<=len; uni++) {
                infoout.append(" ").append(sedit.words[uni - 1]).append("\n").append(uni + 1);
            }
            JOptionPane.showMessageDialog(p2 , infoout.toString(), "基本信息",JOptionPane.WARNING_MESSAGE);
            System.out.println(infoout);
            cl.show(cards,"editinfo");

        });

        JButton baseinfo = new JButton("基本信息查看");
        p1.add(baseinfo);
        baseinfo.addActionListener(e -> {
            Vector<Vector<String>> vec = null;
            try {
                vec = ReadFile("out.txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            assert vec != null;
            int len = vec.size();
            String baseinfo1;
            baseinfo1 = "总人数：" + len + "\n";
            double aver=0;
            double max=0,min=99999;
            for (Vector<String> stringVector : vec) {
                if (Double.parseDouble(stringVector.get(3)) <= min) {
                    min = Double.parseDouble(stringVector.get(3));
                }
                if (Double.parseDouble(stringVector.get(3)) >= max) {
                    max = Double.parseDouble(stringVector.get(3));
                }
                aver = aver + Double.parseDouble(stringVector.get(3));
            }
            aver = aver/len;
            baseinfo1 = baseinfo1 + "平均工资：" + aver + "\n";
            StringBuilder maxinfo = new StringBuilder();
            StringBuilder mininfo = new StringBuilder();
            for (Vector<String> strings : vec) {
                if (Double.parseDouble(strings.get(3)) == max) {
                    maxinfo.append(strings).append("\n");
                }
                if (Double.parseDouble(strings.get(3)) == min) {
                    mininfo.append(strings).append("\n");
                }
            }
            baseinfo1 = baseinfo1 + "最高工资信息：\n" + maxinfo + "最低工资信息：\n" + mininfo;


            JOptionPane.showMessageDialog(p2 , baseinfo1, "基本信息",JOptionPane.WARNING_MESSAGE);
        });


        cards.add(p1,"card1");

        cl.show(cards,"card1");
        frame.add(cards);
        frame.setBounds(300,200,800,200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
