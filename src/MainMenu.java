
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JFormattedTextField;
import java.util.Calendar;
import java.sql.*;   // for con, rs, stmt i.e for connectivity
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.text.DateFormatter;
import javax.swing.JTextField;

public class MainMenu extends javax.swing.JFrame {
Connection con=null;
ResultSet rs=null;
Statement stmt=null;
String query;String Contact;String cursor;String selectedContactName;String readTxt, contactInfo;

String selectedTime;
static int nextSelection;    

CardLayout cl;
int length;
static int count;
static int count2;
Calendar cal=Calendar.getInstance();

String[] splitName;
String[] SplitLog;
void callKeypad(JTextField tf, String s)      // can also use getSource()
{

                readTxt= tf.getText();      // reads tha already writeen text in textfield
               
                if(NumberButton.isSelected())
                      {
                           String Op= String.valueOf(s.charAt(1)); 
                          tf.setText(readTxt+ Op); //this will append to previous txt;ascii converted to alphabet

                      }
               else if(ShiftButtton.isSelected())
                      {
                        String alphabet= String.valueOf(s.charAt(0)); //get first char i.e cap alphabet and convert to String
                        String lowcase= alphabet.toLowerCase();          //Converts it to small case
                        int smallAlphabet=lowcase.compareTo("a");       //compares will return ascii of bigger alphabet 
                        tf.setText(readTxt+ (char)(97+ smallAlphabet)); //this will append to previous txt;ascii converted to alphabet

                       }
               else if(s.equals(",.")|| s.equals("#&"))
               {
                   if(NumberButton.isSelected())
                      {
                           String Op= String.valueOf(s.charAt(1)); 
                          tf.setText(readTxt+ Op); //this will append to previous txt;ascii converted to alphabet
                      }
                   else
                   {
                       String Op= String.valueOf(s.charAt(0)); 
                          tf.setText(readTxt+ Op); //this will append to previous txt;ascii converted to alphabet
                   }
                   
               }
                else // by default capital alphabets
                {
                 String char1= String.valueOf(s.charAt(0)); //get first char on key and convert to String
                 int CapAlphabet=char1.compareTo("A");         //the char is compared to A, diff of ascii value obtained 
                tf.setText(readTxt+ (char)(65+CapAlphabet)); // original ascii of key pressed is 65 + difference
                }                               
}


void callLog()
{
 LogPanel.setVisible(true);
        MainMenu.setVisible(false);
DefaultListModel list=new DefaultListModel();// creating an object whose name is list

        query="select * from callregister";
        try{
            rs= stmt.executeQuery(query); // rs is result set it has all the data of database now
            while(rs.next())  // here while because we want all logs when we open log panel
            {
               // jTextArea2.append(rs.getString("callTime")+"  "+rs.getString("contact")+"\n"); //if we had textarea, not list
           list.addElement(rs.getString("Name")+"  "+rs.getString("contact")+"  "+rs.getString("callTime"));
           LogList.setModel(list);
           
            //count2++;
           LogList.setSelectedIndex(0);

            }
            count2=LogList.getModel().getSize();  
        }
catch(SQLException e)
{
    JOptionPane.showMessageDialog(null, "sql exception in callLog() "+e);
    
}
}
void callDial()
{
    DialPanel.setVisible(true);
        MainMenu.setVisible(false);
    SavePanel.setVisible(false);
DialField.setText("");
}
void callDial2()
{
    DialPanel2.setVisible(true);
    DialPanel.setVisible(false);
    callingLabel.setText("calling \n"+DialField.getText());


}
void callNotes()
{
    NotesPanel.setVisible(true);
        MainMenu.setVisible(false);
        query="select * from notes ";
         try{
             rs= stmt.executeQuery(query);
       
 }
         catch(SQLException e)
                 {
                     JOptionPane.showMessageDialog(null, "sql except in executing load note query");
                 }
 fetchnotes();
        
   
}
void fetchnotes()
{
   
         try{
            
       if(rs.next()) //it has row 1
       {
        jFormattedTextField2.setText(rs.getString("date"));  
        jTextArea3.setText(rs.getString("text"));
       }
       else
       {
           rs.first();
           jFormattedTextField2.setText(rs.getString("date"));  
        jTextArea3.setText(rs.getString("text"));
       }
 }
         catch(SQLException e)
                 {
                     JOptionPane.showMessageDialog(null, "sql except in executing load note query");
                 }
}
void callEditnote()
{
    EditNotePanel.setVisible(true);
    DateField.requestFocus(); // to bring cursor on the date field when notes is opened
    NotesPanel.setVisible(false);

}
void callContacts()
{
    
    ContactsPanel.setVisible(true);
    ContactsList.setEnabled(true);
        MainMenu.setVisible(false);
    SavePanel.setVisible(false);


DefaultListModel list=new DefaultListModel(); // creating an object whose name is list
String query="select FirstName,LastName from contacts ";
try{
rs=stmt.executeQuery(query);
while(rs.next())
{
    list.addElement(rs.getString("FirstName")+" "+rs.getString("LastName"));//  taking value in list
               // model means value; hence setting value to list i.e this object which has all data now
    ContactsList.setModel(list);
    count=ContactsList.getModel().getSize();
    ContactsList.setSelectedIndex(0);


}

}
catch(SQLException e)
{
   JOptionPane.showMessageDialog(null, "sql except in retrieving contacts "+e);
}

}
void callSave()
{

    SavePanel.setVisible(true);
    DialPanel.setVisible(false);
    firstfield.requestFocus();
    lastfield.setText("");
    firstfield.setText("");
    numberfield.setText(DialField.getText());

   
    
}

void callCalc()
{
    
    NumberButton.setSelected(true);
    MainMenu.setVisible(false);
     
    Result.setEditable(false);
    CalcPanel.setVisible(true);
    Num1Field.requestFocus();
  
}

void callImage()
{
        ImagePanel.setVisible(true);
            ImagePanel2.setVisible(false);

        MainMenu.setVisible(false);
        DisplayImage.setVisible(true);
        Pizza.setEnabled(true);
        Flower.setEnabled(false);
        Minion.setEnabled(false);
            
}

void callImage2()
{
    ImagePanel2.setVisible(true);
    ImagePanel.setVisible(false);
    
    if(Minion.isEnabled())
    {
     ZoomImage.setIcon(new ImageIcon("file:/C:/Users/Pawan/Desktop/mobile project/minion uploaded.png"));
    }
   
    else if(Flower.isEnabled())
    {
     ZoomImage.setIcon(new ImageIcon(" file:/C:/Users/Pawan/Desktop/mobile project/flower uploaded.png"));
    }
   
    else if(Pizza.isEnabled())
    {
     ZoomImage.setIcon(new ImageIcon(" file:/C:/Users/Pawan/Desktop/mobile project/pizza uploaded.png"));
    }
   

}


void callMain()
{

    
    MainMenu.setVisible(true);
        ImagePanel.setVisible(false);
LogPanel.setVisible(false);
CalcPanel.setVisible(false);
DialPanel.setVisible(false);
DialPanel2.setVisible(false);
EditNotePanel.setVisible(false);
ImagePanel2.setVisible(false);
SavePanel.setVisible(false);
DisplayImage.setVisible(false);
ContactsPanel.setVisible(false);
NotesPanel.setVisible(false);
}
//void call_NumberButton_Fuc()
//{
//    NumberButton.setSelected(true);
//}
    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HomeScreen = new javax.swing.JPanel();
        MainMenu = new javax.swing.JPanel();
        Icons = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        Labels = new javax.swing.JPanel();
        Images = new javax.swing.JLabel();
        Contacts = new javax.swing.JLabel();
        Log = new javax.swing.JLabel();
        DialPanel = new javax.swing.JPanel();
        DialField = new javax.swing.JTextField();
        DialLabels = new javax.swing.JPanel();
        DialLabel = new javax.swing.JLabel();
        AddtoContLbel = new javax.swing.JLabel();
        DialDelLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        DialPanel2 = new javax.swing.JPanel();
        callingLabel = new javax.swing.JLabel();
        SavePanel = new javax.swing.JPanel();
        firstfield = new javax.swing.JTextField();
        numberfield = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lastfield = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        SaveLabels = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        ContactsPanel = new javax.swing.JPanel();
        ContList = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ContactsList = new javax.swing.JList();
        ContactLabels = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        LogPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        LogList = new javax.swing.JList();
        LogLabels = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        NotesPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        NotesLabels = new javax.swing.JPanel();
        Delete = new javax.swing.JLabel();
        New = new javax.swing.JLabel();
        Next = new javax.swing.JLabel();
        EditNotePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        DateField = new javax.swing.JFormattedTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        NoteArea = new javax.swing.JTextArea();
        EditNotesLabels = new javax.swing.JPanel();
        Delete1 = new javax.swing.JLabel();
        New1 = new javax.swing.JLabel();
        Next1 = new javax.swing.JLabel();
        CalcPanel = new javax.swing.JPanel();
        TextFields = new javax.swing.JPanel();
        Num1Field = new javax.swing.JTextField();
        OperatorField = new javax.swing.JTextField();
        Num2Field = new javax.swing.JTextField();
        Result = new javax.swing.JTextField();
        CalcLabels = new javax.swing.JPanel();
        CalcClear = new javax.swing.JLabel();
        CalcResult = new javax.swing.JLabel();
        CalcDelete = new javax.swing.JLabel();
        ImagePanel = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        DisplayImage = new javax.swing.JPanel();
        Flower = new javax.swing.JLabel();
        Pizza = new javax.swing.JLabel();
        Minion = new javax.swing.JLabel();
        ImagePanel2 = new javax.swing.JPanel();
        ZoomImage = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        Message = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        KeyPad = new javax.swing.JPanel();
        KeyTop = new javax.swing.JPanel();
        TopLeft = new javax.swing.JPanel();
        BackKey = new javax.swing.JButton();
        DialKey = new javax.swing.JButton();
        TopCentre = new javax.swing.JPanel();
        OkButton = new javax.swing.JButton();
        TopRight = new javax.swing.JPanel();
        NextButton = new javax.swing.JButton();
        CanKey = new javax.swing.JButton();
        KeyCentre = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        KeyBottom = new javax.swing.JPanel();
        jButton28 = new javax.swing.JButton();
        EnterKey = new javax.swing.JButton();
        NumberButton = new javax.swing.JToggleButton();
        ShiftButtton = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(400, 400));
        setSize(new java.awt.Dimension(500, 200));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        HomeScreen.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "123...", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        HomeScreen.setPreferredSize(new java.awt.Dimension(417, 230));
        HomeScreen.setLayout(new java.awt.CardLayout());

        MainMenu.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Icons.setLayout(new java.awt.GridLayout(2, 2, 50, 20));

        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });
        jLabel33.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jLabel33CaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        Icons.add(jLabel33);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel32MouseEntered(evt);
            }
        });
        Icons.add(jLabel32);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
        });
        Icons.add(jLabel30);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel31MouseClicked(evt);
            }
        });
        Icons.add(jLabel31);

        Labels.setLayout(new java.awt.GridLayout(1, 0));

        Images.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Images.setText("Images");
        Images.setToolTipText("");
        Labels.add(Images);

        Contacts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Contacts.setText("Contacts");
        Contacts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ContactsMouseClicked(evt);
            }
        });
        Labels.add(Contacts);

        Log.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Log.setText("Log");
        Labels.add(Log);

        javax.swing.GroupLayout MainMenuLayout = new javax.swing.GroupLayout(MainMenu);
        MainMenu.setLayout(MainMenuLayout);
        MainMenuLayout.setHorizontalGroup(
            MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Labels, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Icons, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
                .addContainerGap())
        );
        MainMenuLayout.setVerticalGroup(
            MainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainMenuLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(Icons, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Labels, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        HomeScreen.add(MainMenu, "card2");

        DialField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                DialFieldFocusLost(evt);
            }
        });
        DialField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DialFieldActionPerformed(evt);
            }
        });

        DialLabels.setLayout(new java.awt.GridLayout(1, 0));

        DialLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DialLabel.setText("Dial");
        DialLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DialLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DialLabelMouseEntered(evt);
            }
        });
        DialLabels.add(DialLabel);

        AddtoContLbel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AddtoContLbel.setText("Add to Contacts");
        AddtoContLbel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddtoContLbelMouseClicked(evt);
            }
        });
        DialLabels.add(AddtoContLbel);

        DialDelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DialDelLabel.setText("Delete");
        DialLabels.add(DialDelLabel);

        jLabel2.setText("set size of jframe.; on closing some exception; date invisible in note ; except in dial");

        javax.swing.GroupLayout DialPanelLayout = new javax.swing.GroupLayout(DialPanel);
        DialPanel.setLayout(DialPanelLayout);
        DialPanelLayout.setHorizontalGroup(
            DialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(DialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialPanelLayout.createSequentialGroup()
                        .addComponent(DialField)
                        .addGap(32, 32, 32))))
            .addGroup(DialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(DialPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(DialLabels, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        DialPanelLayout.setVerticalGroup(
            DialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(DialField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(DialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialPanelLayout.createSequentialGroup()
                    .addContainerGap(174, Short.MAX_VALUE)
                    .addComponent(DialLabels, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        HomeScreen.add(DialPanel, "card3");

        callingLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        callingLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout DialPanel2Layout = new javax.swing.GroupLayout(DialPanel2);
        DialPanel2.setLayout(DialPanel2Layout);
        DialPanel2Layout.setHorizontalGroup(
            DialPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(callingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                .addContainerGap())
        );
        DialPanel2Layout.setVerticalGroup(
            DialPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(callingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        HomeScreen.add(DialPanel2, "card4");

        firstfield.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        firstfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                firstfieldFocusGained(evt);
            }
        });

        numberfield.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numberfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                numberfieldFocusGained(evt);
            }
        });

        jLabel1.setText("Number");

        jLabel28.setText("FirstName");

        lastfield.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lastfield.setText("delete from middle");
        lastfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lastfieldFocusGained(evt);
            }
        });

        jLabel29.setText("LastName");

        SaveLabels.setLayout(new java.awt.GridLayout(1, 0));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Back");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel12MouseEntered(evt);
            }
        });
        SaveLabels.add(jLabel12);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Save");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        SaveLabels.add(jLabel13);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Delete");
        SaveLabels.add(jLabel14);

        javax.swing.GroupLayout SavePanelLayout = new javax.swing.GroupLayout(SavePanel);
        SavePanel.setLayout(SavePanelLayout);
        SavePanelLayout.setHorizontalGroup(
            SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SavePanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SavePanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(23, 381, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SavePanelLayout.createSequentialGroup()
                        .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 89, Short.MAX_VALUE)
                        .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastfield, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstfield, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))))
            .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SavePanelLayout.createSequentialGroup()
                    .addContainerGap(156, Short.MAX_VALUE)
                    .addComponent(numberfield, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(43, 43, 43)))
            .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SavePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(SaveLabels, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        SavePanelLayout.setVerticalGroup(
            SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SavePanelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstfield, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(18, 18, 18)
                .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastfield, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addContainerGap(66, Short.MAX_VALUE))
            .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SavePanelLayout.createSequentialGroup()
                    .addGap(48, 48, 48)
                    .addComponent(numberfield, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(164, Short.MAX_VALUE)))
            .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SavePanelLayout.createSequentialGroup()
                    .addContainerGap(200, Short.MAX_VALUE)
                    .addComponent(SaveLabels, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        HomeScreen.add(SavePanel, "card5");

        ContactsPanel.setLayout(new javax.swing.BoxLayout(ContactsPanel, javax.swing.BoxLayout.Y_AXIS));

        ContList.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacts\n"));
        ContList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ContListFocusGained(evt);
            }
        });
        ContList.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                ContListComponentShown(evt);
            }
        });

        ContactsList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ContactsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ContactsList.setSelectedIndex(0);
        ContactsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ContactsListMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ContactsListMouseEntered(evt);
            }
        });
        ContactsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ContactsListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(ContactsList);

        javax.swing.GroupLayout ContListLayout = new javax.swing.GroupLayout(ContList);
        ContList.setLayout(ContListLayout);
        ContListLayout.setHorizontalGroup(
            ContListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addContainerGap())
        );
        ContListLayout.setVerticalGroup(
            ContListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        ContactsPanel.add(ContList);

        ContactLabels.setLayout(new java.awt.GridLayout(1, 0));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Delete");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel15MouseEntered(evt);
            }
        });
        ContactLabels.add(jLabel15);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Dial");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        ContactLabels.add(jLabel16);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Next");
        ContactLabels.add(jLabel17);

        ContactsPanel.add(ContactLabels);

        HomeScreen.add(ContactsPanel, "card6");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Call Log"));

        LogList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "            count2=LogList.getModel().getSize();  ", " ", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        LogList.setRequestFocusEnabled(false);
        LogList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogListMouseClicked(evt);
            }
        });
        LogList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                LogListPropertyChange(evt);
            }
        });
        LogList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                LogListValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(LogList);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
        );

        LogLabels.setLayout(new java.awt.GridLayout(1, 0));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("Del");
        LogLabels.add(jLabel21);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Next");
        LogLabels.add(jLabel22);

        javax.swing.GroupLayout LogPanelLayout = new javax.swing.GroupLayout(LogPanel);
        LogPanel.setLayout(LogPanelLayout);
        LogPanelLayout.setHorizontalGroup(
            LogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(LogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LogPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(LogLabels, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)))
        );
        LogPanelLayout.setVerticalGroup(
            LogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LogPanelLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 59, Short.MAX_VALUE))
            .addGroup(LogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LogPanelLayout.createSequentialGroup()
                    .addContainerGap(201, Short.MAX_VALUE)
                    .addComponent(LogLabels, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        HomeScreen.add(LogPanel, "card7");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Notes"));

        jFormattedTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder("Date"));

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setBorder(javax.swing.BorderFactory.createTitledBorder("Note"));
        jScrollPane4.setViewportView(jTextArea3);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFormattedTextField2)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
        );

        NotesLabels.setLayout(new java.awt.GridLayout(1, 0));

        Delete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Delete.setText("Delete");
        Delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DeleteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DeleteMouseEntered(evt);
            }
        });
        NotesLabels.add(Delete);

        New.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        New.setText("New");
        New.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NewMouseClicked(evt);
            }
        });
        NotesLabels.add(New);

        Next.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Next.setText("Next");
        NotesLabels.add(Next);

        javax.swing.GroupLayout NotesPanelLayout = new javax.swing.GroupLayout(NotesPanel);
        NotesPanel.setLayout(NotesPanelLayout);
        NotesPanelLayout.setHorizontalGroup(
            NotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NotesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(NotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(NotesPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(NotesLabels, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        NotesPanelLayout.setVerticalGroup(
            NotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NotesPanelLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 49, Short.MAX_VALUE))
            .addGroup(NotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NotesPanelLayout.createSequentialGroup()
                    .addGap(0, 201, Short.MAX_VALUE)
                    .addComponent(NotesLabels, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        HomeScreen.add(NotesPanel, "card8");

        EditNotePanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                EditNotePanelMouseMoved(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Notes"));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
        });

        DateField.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Date(yyyy-mm-dd)"));
        DateField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                DateFieldFocusGained(evt);
            }
        });

        NoteArea.setColumns(20);
        NoteArea.setLineWrap(true);
        NoteArea.setRows(5);
        NoteArea.setText("visible row count for max no of rows visible!\n\n\n\n");
        NoteArea.setWrapStyleWord(true);
        NoteArea.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Note"));
        NoteArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                NoteAreaFocusGained(evt);
            }
        });
        jScrollPane3.setViewportView(NoteArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                    .addComponent(DateField, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(DateField, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        EditNotesLabels.setLayout(new java.awt.GridLayout(1, 0));

        Delete1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Delete1.setText("Edit Date");
        Delete1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Delete1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Delete1MouseEntered(evt);
            }
        });
        EditNotesLabels.add(Delete1);

        New1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        New1.setText("Save");
        New1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                New1MouseClicked(evt);
            }
        });
        EditNotesLabels.add(New1);

        Next1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Next1.setText("Delete");
        EditNotesLabels.add(Next1);

        javax.swing.GroupLayout EditNotePanelLayout = new javax.swing.GroupLayout(EditNotePanel);
        EditNotePanel.setLayout(EditNotePanelLayout);
        EditNotePanelLayout.setHorizontalGroup(
            EditNotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditNotePanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(EditNotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(EditNotePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(EditNotesLabels, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        EditNotePanelLayout.setVerticalGroup(
            EditNotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditNotePanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 42, Short.MAX_VALUE))
            .addGroup(EditNotePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditNotePanelLayout.createSequentialGroup()
                    .addContainerGap(207, Short.MAX_VALUE)
                    .addComponent(EditNotesLabels, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        HomeScreen.add(EditNotePanel, "card9");

        TextFields.setLayout(new javax.swing.BoxLayout(TextFields, javax.swing.BoxLayout.Y_AXIS));

        Num1Field.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Num1"));
        Num1Field.setNextFocusableComponent(OperatorField);
        Num1Field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Num1FieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Num1FieldFocusLost(evt);
            }
        });
        Num1Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Num1FieldActionPerformed(evt);
            }
        });
        TextFields.add(Num1Field);

        OperatorField.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Operator"));
        OperatorField.setNextFocusableComponent(Num2Field);
        OperatorField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                OperatorFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                OperatorFieldFocusLost(evt);
            }
        });
        TextFields.add(OperatorField);

        Num2Field.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Num2"));
        Num2Field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Num2FieldFocusGained(evt);
            }
        });
        TextFields.add(Num2Field);

        Result.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Result"));
        TextFields.add(Result);

        CalcLabels.setLayout(new java.awt.GridLayout(1, 0));

        CalcClear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CalcClear.setText("ClearAll");
        CalcLabels.add(CalcClear);

        CalcResult.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CalcResult.setText("Result");
        CalcLabels.add(CalcResult);

        CalcDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CalcDelete.setText("Delete");
        CalcDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CalcDeleteMouseClicked(evt);
            }
        });
        CalcLabels.add(CalcDelete);

        javax.swing.GroupLayout CalcPanelLayout = new javax.swing.GroupLayout(CalcPanel);
        CalcPanel.setLayout(CalcPanelLayout);
        CalcPanelLayout.setHorizontalGroup(
            CalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CalcPanelLayout.createSequentialGroup()
                .addComponent(TextFields, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(CalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CalcPanelLayout.createSequentialGroup()
                    .addComponent(CalcLabels, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        CalcPanelLayout.setVerticalGroup(
            CalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CalcPanelLayout.createSequentialGroup()
                .addComponent(TextFields, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 46, Short.MAX_VALUE))
            .addGroup(CalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CalcPanelLayout.createSequentialGroup()
                    .addGap(0, 214, Short.MAX_VALUE)
                    .addComponent(CalcLabels, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        HomeScreen.add(CalcPanel, "CalcPanel");

        jLabel24.setText("View");

        DisplayImage.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Images\n\n"));
        DisplayImage.setAutoscrolls(true);
        DisplayImage.setLayout(new java.awt.GridLayout(1, 0));

        Flower.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Flower.setIcon(new javax.swing.ImageIcon(getClass().getResource("/flower uploaded.png"))); // NOI18N
        Flower.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DisplayImage.add(Flower);

        Pizza.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Pizza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pizza uploaded.png"))); // NOI18N
        Pizza.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DisplayImage.add(Pizza);

        Minion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Minion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/minion uploaded.png"))); // NOI18N
        Minion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DisplayImage.add(Minion);

        javax.swing.GroupLayout ImagePanelLayout = new javax.swing.GroupLayout(ImagePanel);
        ImagePanel.setLayout(ImagePanelLayout);
        ImagePanelLayout.setHorizontalGroup(
            ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanelLayout.createSequentialGroup()
                .addGap(21, 226, Short.MAX_VALUE)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(166, 166, 166))
            .addGroup(ImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DisplayImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ImagePanelLayout.setVerticalGroup(
            ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DisplayImage, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addContainerGap())
        );

        HomeScreen.add(ImagePanel, "card12");

        ZoomImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel38.setText("Back");

        javax.swing.GroupLayout ImagePanel2Layout = new javax.swing.GroupLayout(ImagePanel2);
        ImagePanel2.setLayout(ImagePanel2Layout);
        ImagePanel2Layout.setHorizontalGroup(
            ImagePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanel2Layout.createSequentialGroup()
                .addGroup(ImagePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ImagePanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel38))
                    .addGroup(ImagePanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(ZoomImage, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        ImagePanel2Layout.setVerticalGroup(
            ImagePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ZoomImage, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        HomeScreen.add(ImagePanel2, "card11");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Send");

        javax.swing.GroupLayout MessageLayout = new javax.swing.GroupLayout(Message);
        Message.setLayout(MessageLayout);
        MessageLayout.setHorizontalGroup(
            MessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MessageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MessageLayout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        MessageLayout.setVerticalGroup(
            MessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MessageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addContainerGap())
        );

        HomeScreen.add(Message, "card13");

        getContentPane().add(HomeScreen);

        KeyPad.setLayout(new javax.swing.BoxLayout(KeyPad, javax.swing.BoxLayout.Y_AXIS));

        KeyTop.setLayout(new javax.swing.BoxLayout(KeyTop, javax.swing.BoxLayout.X_AXIS));

        BackKey.setText("<...");
        BackKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackKeyActionPerformed(evt);
            }
        });

        DialKey.setText("Dial");
        DialKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DialKeyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TopLeftLayout = new javax.swing.GroupLayout(TopLeft);
        TopLeft.setLayout(TopLeftLayout);
        TopLeftLayout.setHorizontalGroup(
            TopLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TopLeftLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(TopLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BackKey, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DialKey, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        TopLeftLayout.setVerticalGroup(
            TopLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopLeftLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(BackKey)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DialKey)
                .addContainerGap())
        );

        KeyTop.add(TopLeft);

        OkButton.setText("OK");
        OkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TopCentreLayout = new javax.swing.GroupLayout(TopCentre);
        TopCentre.setLayout(TopCentreLayout);
        TopCentreLayout.setHorizontalGroup(
            TopCentreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopCentreLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(OkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        TopCentreLayout.setVerticalGroup(
            TopCentreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopCentreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OkButton, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                .addContainerGap())
        );

        KeyTop.add(TopCentre);

        NextButton.setText("...>");
        NextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextButtonActionPerformed(evt);
            }
        });

        CanKey.setText("Can");
        CanKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CanKeyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TopRightLayout = new javax.swing.GroupLayout(TopRight);
        TopRight.setLayout(TopRightLayout);
        TopRightLayout.setHorizontalGroup(
            TopRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 149, Short.MAX_VALUE)
            .addGroup(TopRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TopRightLayout.createSequentialGroup()
                    .addContainerGap(29, Short.MAX_VALUE)
                    .addGroup(TopRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(NextButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CanKey, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap()))
        );
        TopRightLayout.setVerticalGroup(
            TopRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
            .addGroup(TopRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TopRightLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(NextButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CanKey)
                    .addGap(16, 16, 16)))
        );

        KeyTop.add(TopRight);

        KeyPad.add(KeyTop);

        KeyCentre.setLayout(new java.awt.GridLayout(4, 7, 8, 8));

        jButton6.setText("A/");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton6);

        jButton7.setText("B+");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton7);

        jButton8.setText("jkj");
        jButton8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButton8FocusGained(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton8);

        jButton9.setText("D2");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton9);

        jButton10.setText("E3");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton10);

        jButton11.setText("F(");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton11);

        jButton13.setText("G)");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton13);

        jButton14.setText("H~");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton14);

        jButton15.setText("I-");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton15);

        jButton16.setText("J4");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton16);

        jButton17.setText("K5");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton17);

        jButton18.setText("L6");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton18);

        jButton19.setText("M:");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton19);

        jButton20.setText("N;");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton20);

        jButton21.setText("O@");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton21);

        jButton22.setText("P*");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton22);

        jButton23.setText("Q7");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton23);

        jButton24.setText("R8");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton24);

        jButton25.setText("S9");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton25);

        jButton26.setText("T'");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton26);

        jButton27.setText("U\"");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton27);

        jButton35.setText("#&");
        jButton35.setToolTipText("");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton35);

        jButton36.setText("V=");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton36);

        jButton37.setText("W$");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton37);

        jButton38.setText("X0");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton38);

        jButton39.setText("Y%");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton39);

        jButton40.setText("Z?");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton40);

        jButton41.setText(",.");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DigPressed(evt);
            }
        });
        KeyCentre.add(jButton41);

        KeyPad.add(KeyCentre);

        KeyBottom.setLayout(new java.awt.GridLayout(1, 4, 25, 15));

        jButton28.setText("SPACE");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        KeyBottom.add(jButton28);

        EnterKey.setText("ENTER");
        EnterKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnterKeyActionPerformed(evt);
            }
        });
        KeyBottom.add(EnterKey);

        NumberButton.setText("12.,^");
        NumberButton.setActionCommand("Shift");
        NumberButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumberButtonActionPerformed(evt);
            }
        });
        NumberButton.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                NumberButtonPropertyChange(evt);
            }
        });
        KeyBottom.add(NumberButton);

        ShiftButtton.setText("Shift");
        ShiftButtton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShiftButttonActionPerformed(evt);
            }
        });
        KeyBottom.add(ShiftButtton);

        KeyPad.add(KeyBottom);

        getContentPane().add(KeyPad);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CanKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CanKeyActionPerformed
          callMain();


        // TODO add your handling code here:
    }//GEN-LAST:event_CanKeyActionPerformed

    private void NextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextButtonActionPerformed
if(MainMenu.isVisible())
        {        
        callLog();
        }
else if(DialPanel.isVisible())
        {
            readTxt=DialField.getText();
            length= DialField.getText().length();
            if(length>0)
                {
                    DialField.setText(readTxt.substring(0, length-1));}
                 }
else if(CalcPanel.isVisible())
{
     
    switch (cursor)
    {
        case "num1":
           
            length=Num1Field.getText().length();
            if(length>0)
            {Num1Field.setText(Num1Field.getText().substring(0, length-1)); }
             break;
      
       
             case "num2":
                
                 length=Num2Field.getText().length();   
                 if(length>0)
                 {Num2Field.setText(Num2Field.getText().substring(0, length-1));
                 }break;
            
        case "operator":
            OperatorField.setText("");
    }
}
else if(SavePanel.isVisible())
{
    switch(cursor)
    {
        case "SaveNumber":
            
            length= numberfield.getText().length(); // gets the total length of text typed
            if(length>0)
            {numberfield.setText(numberfield.getText().substring(0, length-1)); //new text does not have last char now
            }
        break;
        
         case "FirstName":
            
            length= firstfield.getText().length(); // gets the total length of text typed
                        if(length>0)

                        {firstfield.setText(firstfield.getText().substring(0, length-1)); //new text does not have last char now
                        }
         break;
        case "LastName":
           
            length= lastfield.getText().length(); // gets the total length of text typed
                if(length>0)

                {lastfield.setText(lastfield.getText().substring(0, length-1)); //new text does not have last char now
                }
        break;
            
        
    }
}

else if(EditNotePanel.isVisible())
{
    switch(cursor)
    {
        case "datefield":
            
            length= DateField.getText().length(); // gets the total length of text typed
           if(length>0)
           {DateField.setText(DateField.getText().substring(0, length-1)); //new text does not have last char now
           }
        break;
            
        
        case "notesfield":
          length= NoteArea.getText().length();
               
          if(length>0)
          { 
               NoteArea.replaceRange("", length-1, length); //replaces last char by " "
          }
        break;
            
    }
}


else if(ContactsPanel.isVisible())
{
    
    try{
    if(nextSelection==count-1)
        {
            
            ContactsList.setSelectedIndex(0);
            nextSelection=0;
        }
    else 
    {
    nextSelection++;
    ContactsList.setSelectedIndex(nextSelection);
    }
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null,"exception in contact panel next button"+e);
    }
    
}
else if(LogPanel.isVisible())
{
   try{
    if(nextSelection==count2 - 1)
        {
            
            LogList.setSelectedIndex(0);
            nextSelection=0;
        }
    else 
    {
    nextSelection++;
    LogList.setSelectedIndex(nextSelection);
    }
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null,"exception in log panel next button"+e);
    } 
    
    
   
}
else if(NotesPanel.isVisible())
{
    fetchnotes();
}

else if(ImagePanel.isVisible())
    {
    Minion.setEnabled(true);
     
    Flower.setEnabled(false);
           
    Pizza.setEnabled(false);

    callImage2();
    }

// TODO add your handling code here:
    }//GEN-LAST:event_NextButtonActionPerformed

    private void OkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkButtonActionPerformed
if(MainMenu.isVisible())
{       
    callContacts();

}

else if(NotesPanel.isVisible())
{ 
  callEditnote();
   
}
else if(EditNotePanel.isVisible())
    {
      // DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
   //jFormattedTextField1 = new JFormattedTextField(format);
   query="insert into notes(date,text) values('"+DateField.getText()+"','"+NoteArea.getText()+"')";
   try{
       int i=stmt.executeUpdate(query);
   }
   catch(SQLException e)
   {
     JOptionPane.showMessageDialog(null, "sql except in edit note"+e);
   }
   JOptionPane.showMessageDialog(getContentPane(), "Note saved successfully!");
   }

else if(DialPanel.isVisible())
    {
        callSave();
    }
else if(SavePanel.isVisible())
    {


        query= "insert into contacts values('"+firstfield.getText()+"','"+lastfield.getText()+"',"+numberfield.getText()+")";
       try{
    int i=stmt.executeUpdate(query);

        firstfield.setFocusable(false);
        lastfield.setFocusable(false);
        numberfield.setFocusable(false);
    }
    catch(SQLException e)

    {
       JOptionPane.showMessageDialog(null, "sql update except in save panel");
    }

       callContacts();
    }
else if(ImagePanel.isVisible())
{
    
            Pizza.setEnabled(true);


    callImage2();
    
}

else if(CalcPanel.isVisible())
{
    try
    {
    if(OperatorField.getText().equals("+")) 
{
         int sum=Integer.parseInt(Num1Field.getText())+Integer.parseInt(Num2Field.getText());
         Result.setText(String.valueOf(sum));
}
    if(OperatorField.getText().equals("*")) 
{
         int mul=Integer.parseInt(Num1Field.getText())*Integer.parseInt(Num2Field.getText());
         Result.setText(String.valueOf(mul));
}
    if(OperatorField.getText().equals("-")) 
{
         int diff=Integer.parseInt(Num1Field.getText())-Integer.parseInt(Num2Field.getText());
         Result.setText(String.valueOf(diff));
}
    if(OperatorField.getText().equals("/")) 
{
         int div=Integer.parseInt(Num1Field.getText())/Integer.parseInt(Num2Field.getText());
         Result.setText(String.valueOf(div));
}
}
    catch(NumberFormatException e)
    {
        JOptionPane.showMessageDialog(null, "except in result calc"+e);
    }
}
else if(ContactsPanel.isVisible())
{
    if(ContactsList.isSelectionEmpty())
    {       JOptionPane.showMessageDialog(getContentPane(),"Select a contact!");
}
    else{
    DialPanel2.setVisible(true);
    ContactsPanel.setVisible(false);
    callingLabel.setText("Calling "+ selectedContactName); // this is selected full name
    
// we can find number only if we know both first name and last name

    query="select Number from contacts where FirstName='"+splitName[0] +"' and LastName='"+splitName[1]+"'";
    try{
    rs=stmt.executeQuery(query);  // now rs result set has the no. corres to first name nd last name that we selected
    if(rs.next())
      {
          contactInfo=rs.getString("Number");   // save the number in contactInfo
       }
    }
// TODO add your handling code here:
catch(SQLException e)
{
    JOptionPane.showMessageDialog(null, "Sql except in ok button contacts panel query 1  "+e);
}
    
    // we dial by clicking contact name
    // selected Contact name ; no. corres to it and time goes to call register 
    
query="insert into callregister(Name,contact,callTime) values('"+selectedContactName+"','"+ contactInfo+"','"+cal.getTime()+"')";

try{
    int i=stmt.executeUpdate(query);
}
// TODO add your handling code here:
catch(SQLException e)
{
    JOptionPane.showMessageDialog(null, "Sql except in contacts panel ok button query 2  "+e);
}
    
   
}
}

        
// TODO add your handling code here:
    }//GEN-LAST:event_OkButtonActionPerformed

    private void DialKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DialKeyActionPerformed
if(MainMenu.isVisible()){
        //DialPanel.setVisible(true);
        callDial();
        DialField.requestFocus();
}
else if (DialPanel.isVisible())
{
   
    callDial2();

callingLabel.setText("calling \n"+DialField.getText());

query="insert into callregister(contact,callTime) values("+DialField.getText()+",'"+cal.getTime()+"')";

try{
    int i=stmt.executeUpdate(query);
}
// TODO add your handling code here:
catch(SQLException e)
{
    JOptionPane.showMessageDialog(null, "Sql except in dial panel dialing"+e);
}
    
}
else if(SavePanel.isVisible())
{
    callDial2();
}


// TODO add your handling code here:
    }//GEN-LAST:event_DialKeyActionPerformed

    private void ContactsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContactsMouseClicked


        
// TODO add your handling code here:
    }//GEN-LAST:event_ContactsMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        MainMenu.setVisible(true);
        LogPanel.setVisible(false);
CalcPanel.setVisible(false);
DialPanel.setVisible(false);
DialPanel2.setVisible(false);
EditNotePanel.setVisible(false);
ImagePanel2.setVisible(false);
SavePanel.setVisible(false);
DisplayImage.setVisible(false);
ContactsPanel.setVisible(false);
NotesPanel.setVisible(false);
   
   
try
{                                                // jdbc-java database connectivity
    Class.forName("com.mysql.jdbc.Driver");      // telling name of driver
    con=DriverManager.getConnection("jdbc:mysql://localhost/contactlist","root","root"); // establishing connection with
    stmt=con.createStatement();                                                     //contactlist database having all tables 
                                                                                    //req for mobile. Only one time req 
}                                                                                    //when project window opened
catch(ClassNotFoundException e)
        
{
    
JOptionPane.showMessageDialog(null,"ClassNotFound except "+e);}
catch(SQLException e)
    {
        JOptionPane.showMessageDialog(null,"Error in sql connectivity!");
    }
      // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void AddtoContLbelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddtoContLbelMouseClicked
    //SavePanel.setVisible(true);
    callSave();

        // TODO add your handling code here:
    }//GEN-LAST:event_AddtoContLbelMouseClicked

    private void DialFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_DialFieldFocusLost
numberfield.setText(DialField.getText());
        
// TODO add your handling code here:
    }//GEN-LAST:event_DialFieldFocusLost

    private void BackKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackKeyActionPerformed

if(MainMenu.isVisible())
        {callImage();}
        
else if(SavePanel.isVisible())
            {    callDial();}

else if(EditNotePanel.isVisible())
            {DateField.requestFocus(); }

else if(NotesPanel.isVisible())
  {
    query="delete from notes where date='"+jFormattedTextField2.getText()+"'";
    try{
                 int i = stmt.executeUpdate(query);
                 
        }
   catch(SQLException e)
       {
                 JOptionPane.showMessageDialog(null,"except in del note"+e);
       }
    JOptionPane.showMessageDialog(getContentPane(), "Note Sucessfully Deleted!");
    callNotes(); // to show all notes again; now deleted note will not b displayed
  }
else if(CalcPanel.isVisible())
   {
    Num1Field.setText(" ");
    Num1Field.requestFocus();
    Num2Field.setText(" ");
    OperatorField.setText(" ");
    Result.setText(" ");
    
   }
else if(SavePanel.isVisible())
  { callDial();}
         
else if(ContactsPanel.isVisible())
     {
         if(ContactsList.isSelectionEmpty()==false){
         
    query="delete from contacts where FirstName='"+splitName[0]+"' and LastName='"+splitName[1]+"'";
    try{
                 int i = stmt.executeUpdate(query);
        }
   catch(SQLException e)
       {
                 JOptionPane.showMessageDialog(null,"except in del contact"+e);
       }
       
       JOptionPane.showMessageDialog(getContentPane(), "Contact Sucessfully Deleted!");
       callContacts();
       }}
else if(LogPanel.isVisible())
     {
         
         
    query="delete from callregister where Name='"+SplitLog[0]+" "+SplitLog[1]+"'" ;
    try{
                 int i = stmt.executeUpdate(query);
                 
        }
   catch(SQLException e)
       {
                 JOptionPane.showMessageDialog(null,"except in del log"+e);
       }
           JOptionPane.showMessageDialog(getContentPane(), "Contact Sucessfully Deleted!");
           callLog();
       }
else if(ImagePanel.isVisible())
    {
        Flower.setEnabled(true);
         Minion.setEnabled(false);
         Pizza.setEnabled(false);
        callImage2();

    }
 else if(ImagePanel2.isVisible())
    {
       
        callImage();

    }

// TODO add your handling code here:
    }//GEN-LAST:event_BackKeyActionPerformed

    private void jLabel33CaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jLabel33CaretPositionChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel33CaretPositionChanged

    private void DialLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DialLabelMouseClicked
//DialPanel2.setVisible(true);
callDial2();

Calendar cal=Calendar.getInstance();
System.out.print(cal.getTime());
//insert into callregister (contact,callTime ) values(1122345678," Wed Jul 20 11:44:41 IST 2016" )
query="insert into callregister(contact,callTime) values("+DialField.getText()+",'"+cal.getTime()+"')";
//JOptionPane.showMessageDialog(null,query);
try{
    int i=stmt.executeUpdate(query);
}
// TODO add your handling code here:
catch(SQLException e)
{
    JOptionPane.showMessageDialog(null, "Sql except in dial panel dialing"+e);
}
    }//GEN-LAST:event_DialLabelMouseClicked

    private void ContactsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ContactsListValueChanged

// TODO add your handling code here:
    }//GEN-LAST:event_ContactsListValueChanged

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed

    NoteArea.append(" ");
    NoteArea.requestFocus();
        
// TODO add your handling code here:
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
          callNotes();
        
        
// TODO add your handling code here:
    }//GEN-LAST:event_jLabel32MouseClicked

    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved


        
// TODO add your handling code here:
    }//GEN-LAST:event_jPanel1MouseMoved

    private void EnterKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnterKeyActionPerformed
       NoteArea.requestFocus();
        NoteArea.append("\n ");

if(SavePanel.isVisible())
{
   lastfield.requestFocus();
}
else if(CalcPanel.isVisible())
{
           switch (cursor) {  
               case "num1":      //num1 has focus,thus cursor in num1; cursor=num1;press enter ;if cursor=num1                            
                   OperatorField.requestFocus(); // bring focus to operator field on 
                   break;
               case "operator": // on pressing enter we check what was the value of cursor previously; that is on what field
                   Num2Field.requestFocus();           //it was earlier; coz that will decide next field
                   break;
               case "num2" :
                               Num1Field.requestFocus();           //it was earlier; coz that will decide next field
       
           }
    
}
              
          // TODO add your handling code here:
    }//GEN-LAST:event_EnterKeyActionPerformed

    private void EditNotePanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditNotePanelMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_EditNotePanelMouseMoved

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
cl=(CardLayout)HomeScreen.getLayout();
cl.show(HomeScreen,"CalcPanel");
//CalcPanel.setVisible(true);

       // callCalc();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel33MouseClicked

    private void DialLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DialLabelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_DialLabelMouseEntered

    private void ContListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ContListFocusGained
        // TODO add your handling code here:
        //JOptionPane.showMessageDialog(null,"panel contact");
    }//GEN-LAST:event_ContListFocusGained

    private void ContListComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_ContListComponentShown
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null,"panel contact");
    }//GEN-LAST:event_ContListComponentShown

    private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked
      callLog();
//LogPanel.setVisible(true);

        
// TODO add your handling code here:
    }//GEN-LAST:event_jLabel31MouseClicked

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
            callImage();
// TODO add your handling code here:
    }//GEN-LAST:event_jLabel30MouseClicked

    private void DialFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DialFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DialFieldActionPerformed


@SuppressWarnings("empty-statement")
    private void DigPressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DigPressed
           

        String txt= evt.getActionCommand();

if(txt.equalsIgnoreCase("B+"))
{
      String plusOp= String.valueOf(txt.charAt(1)); // gets second char i.e digit and converts to String
   OperatorField.setText(plusOp);
}
else if(txt.equalsIgnoreCase("a/"))
{
      String divOp= String.valueOf(txt.charAt(1)); // gets second char i.e digit and converts to String
   OperatorField.setText(divOp);
}
else if(txt.equalsIgnoreCase("i-"))
{
      String subOp= String.valueOf(txt.charAt(1)); // gets second char i.e digit and converts to String
   OperatorField.setText(subOp);
}
else if(txt.equalsIgnoreCase("p*"))
{
      String mulOp= String.valueOf(txt.charAt(1)); // gets second char i.e digit and converts to String
   OperatorField.setText(mulOp);
}

  
if(DialPanel.isVisible())
{
       NumberButton.setSelected(true);
        String readTxt= DialField.getText();      // reads tha already written text in textfield
     String Num= String.valueOf(txt.charAt(1)); // gets second char i.e digit and converts to String
    DialField.setText(readTxt+Num.compareTo("0")); //this will append to previous number ; compareto will return bigger int
}
else if(SavePanel.isVisible())
{
    switch(cursor)
    {
        case "FirstName":
            callKeypad(firstfield,txt);
            break;
        case "LastName":
            callKeypad(lastfield,txt);
            break;
        case "SaveNumber":
               callKeypad(numberfield,txt);
                 break;

            
    }
    
}
else if(EditNotePanel.isVisible())
{
  switch (cursor) {  
        case "datefield":                               
                   NumberButton.setSelected(true);      
                    readTxt= DateField.getText(); 
                   String Num= String.valueOf(txt.charAt(1)); 
                    if(Num.compareTo("0")>=0 && Num.compareTo("0")<=9)
                    { 
                         DateField.setText(readTxt+Num.compareTo("0"));
                    }   
                    else if(txt.equalsIgnoreCase("i-"))
                    {
                      DateField.setText(readTxt+ String.valueOf(txt.charAt(1))); 

                    }
                   break;
        case "notesfield": 
                       readTxt= NoteArea.getText();      // reads tha already writeen text in textfield
               if(NumberButton.isSelected())
                      {
                           String Op= String.valueOf(txt.charAt(1)); 
                            NoteArea.append(Op); 
                      }
               else if(ShiftButtton.isSelected())
                      {
                        String alphabet= String.valueOf(txt.charAt(0)); //get first char i.e cap alphabet and convert to String
                        String lowcase= alphabet.toLowerCase();          //Converts it to small case
                        int smallAlphabet=lowcase.compareTo("a");       //compares will return ascii of bigger alphabet 
                        NoteArea.setText(readTxt+ (char)(97+ smallAlphabet)); //this will append to previous txt;ascii converted to alphabet

                       }
               else if(txt.equals(",.")|| txt.equals("#&"))
               {
                   if(NumberButton.isSelected())
                      {
                           String Op= String.valueOf(txt.charAt(1)); 
                            NoteArea.append(Op); 
                      }
                   else
                   {
                       String Op= String.valueOf(txt.charAt(0)); 
                            NoteArea.append(Op); 
                   }
                   
               }
                else // by default capital alphabets
                {
                 String char1= String.valueOf(txt.charAt(0)); //get first char on key and convert to String
                 int CapAlphabet=char1.compareTo("A");         //the char is compared to A, diff of ascii value obtained 
                NoteArea.setText(readTxt+ (char)(65+CapAlphabet)); // original ascii of key pressed is 65 + difference
                }                                                   //(char)(ascii)converts ascii to string value; 
         }  
}
    

else if(CalcPanel.isVisible())
{     NumberButton.setSelected(true);
          String readTxt,Num,readTxt2,Num2;

    switch(cursor)
    {
        case "num1":
            try{ 
            readTxt= Num1Field.getText();      // reads tha already written text in textfield
      Num= String.valueOf(txt.charAt(1)); // gets second char i.e digit and converts to String
       if(Num.compareTo("0")>=0 && Num.compareTo("0")<=9) 
       {
    Num1Field.setText(readTxt+Num.compareTo("0")); //this will append 
       }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(getContentPane(),"Except in calc dig pressed"+e );
            }
        break;
    
        case "num2":
                  try{ 

      readTxt2= Num2Field.getText();      // reads tha already written text in textfield
     Num2= String.valueOf(txt.charAt(1)); // gets second char i.e digit and converts to String
            if(Num2.compareTo("0")>=0 && Num2.compareTo("0")<=9) 
            {
   Num2Field.setText(readTxt2+Num2.compareTo("0")); //this will append 
            }
        
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(getContentPane(),"Except in calc dig pressed"+e );
            }
    }
      
    
}
        
// TODO add your handling code here:
    }//GEN-LAST:event_DigPressed

    private void NumberButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumberButtonActionPerformed
     
        // TODO add your handling code here:
    }//GEN-LAST:event_NumberButtonActionPerformed

    private void ShiftButttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShiftButttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ShiftButttonActionPerformed

    private void Num1FieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Num1FieldFocusLost

        // TODO add your handling code here:
    }//GEN-LAST:event_Num1FieldFocusLost

    private void OperatorFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_OperatorFieldFocusLost
 
        // TODO add your handling code here:
    }//GEN-LAST:event_OperatorFieldFocusLost

    private void Num1FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Num1FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Num1FieldActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
try{
    stmt.close();
rs.close();
con.close();
}
catch(SQLException e)
{
    
}

        
// TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void jButton8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton8FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8FocusGained

    private void Num1FieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Num1FieldFocusGained
        // TODO add your handling code here:
        cursor="num1";
    }//GEN-LAST:event_Num1FieldFocusGained

    private void OperatorFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_OperatorFieldFocusGained
        cursor="operator";

        // TODO add your handling code here:
    }//GEN-LAST:event_OperatorFieldFocusGained

    private void Num2FieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Num2FieldFocusGained
        cursor="num2";

        // TODO add your handling code here:
    }//GEN-LAST:event_Num2FieldFocusGained

    private void NumberButtonPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_NumberButtonPropertyChange
//call_NumberButton_Fuc();
        // TODO add your handling code here:
    }//GEN-LAST:event_NumberButtonPropertyChange

    private void DateFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_DateFieldFocusGained
cursor="datefield";
        // TODO add your handling code here:
    }//GEN-LAST:event_DateFieldFocusGained

    private void NoteAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NoteAreaFocusGained
 cursor= "notesfield";
        // TODO add your handling code here:
    }//GEN-LAST:event_NoteAreaFocusGained

    private void LogListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_LogListPropertyChange


        // TODO add your handling code here:
    }//GEN-LAST:event_LogListPropertyChange

    private void LogListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogListMouseClicked



        // TODO add your handling code here:
    }//GEN-LAST:event_LogListMouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseEntered

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13MouseClicked

    private void DeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_DeleteMouseClicked

    private void DeleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_DeleteMouseEntered

    private void NewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NewMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_NewMouseClicked

    private void CalcDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CalcDeleteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_CalcDeleteMouseClicked

    private void numberfieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_numberfieldFocusGained
cursor="SaveNumber";

        

// TODO add your handling code here:
    }//GEN-LAST:event_numberfieldFocusGained

    private void firstfieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_firstfieldFocusGained
cursor="FirstName";

        // TODO add your handling code here:
    }//GEN-LAST:event_firstfieldFocusGained

    private void lastfieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lastfieldFocusGained
cursor="LastName";

        // TODO add your handling code here:
    }//GEN-LAST:event_lastfieldFocusGained

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel15MouseEntered

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel16MouseClicked

    private void Delete1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Delete1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Delete1MouseClicked

    private void Delete1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Delete1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Delete1MouseEntered

    private void New1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_New1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_New1MouseClicked

    private void LogListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_LogListValueChanged
nextSelection=LogList.getSelectedIndex();

String selectedValue= String.valueOf(LogList.getSelectedValue());
SplitLog=selectedValue.split(" ");

        // TODO add your handling code here:
    }//GEN-LAST:event_LogListValueChanged

    private void ContactsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContactsListMouseClicked
nextSelection =ContactsList.getSelectedIndex();
 selectedContactName= (String)ContactsList.getSelectedValue();
     splitName = selectedContactName.split(" ");
        
// TODO add your handling code here:
    }//GEN-LAST:event_ContactsListMouseClicked

    private void ContactsListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContactsListMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ContactsListMouseEntered

    private void jLabel32MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel32MouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AddtoContLbel;
    private javax.swing.JButton BackKey;
    private javax.swing.JLabel CalcClear;
    private javax.swing.JLabel CalcDelete;
    private javax.swing.JPanel CalcLabels;
    private javax.swing.JPanel CalcPanel;
    private javax.swing.JLabel CalcResult;
    private javax.swing.JButton CanKey;
    private javax.swing.JPanel ContList;
    private javax.swing.JPanel ContactLabels;
    private javax.swing.JLabel Contacts;
    private javax.swing.JList ContactsList;
    private javax.swing.JPanel ContactsPanel;
    private javax.swing.JFormattedTextField DateField;
    private javax.swing.JLabel Delete;
    private javax.swing.JLabel Delete1;
    private javax.swing.JLabel DialDelLabel;
    private javax.swing.JTextField DialField;
    private javax.swing.JButton DialKey;
    private javax.swing.JLabel DialLabel;
    private javax.swing.JPanel DialLabels;
    private javax.swing.JPanel DialPanel;
    private javax.swing.JPanel DialPanel2;
    private javax.swing.JPanel DisplayImage;
    private javax.swing.JPanel EditNotePanel;
    private javax.swing.JPanel EditNotesLabels;
    private javax.swing.JButton EnterKey;
    private javax.swing.JLabel Flower;
    private javax.swing.JPanel HomeScreen;
    private javax.swing.JPanel Icons;
    private javax.swing.JPanel ImagePanel;
    private javax.swing.JPanel ImagePanel2;
    private javax.swing.JLabel Images;
    private javax.swing.JPanel KeyBottom;
    private javax.swing.JPanel KeyCentre;
    private javax.swing.JPanel KeyPad;
    private javax.swing.JPanel KeyTop;
    private javax.swing.JPanel Labels;
    private javax.swing.JLabel Log;
    private javax.swing.JPanel LogLabels;
    private javax.swing.JList LogList;
    private javax.swing.JPanel LogPanel;
    private javax.swing.JPanel MainMenu;
    private javax.swing.JPanel Message;
    private javax.swing.JLabel Minion;
    private javax.swing.JLabel New;
    private javax.swing.JLabel New1;
    private javax.swing.JLabel Next;
    private javax.swing.JLabel Next1;
    private javax.swing.JButton NextButton;
    private javax.swing.JTextArea NoteArea;
    private javax.swing.JPanel NotesLabels;
    private javax.swing.JPanel NotesPanel;
    private javax.swing.JTextField Num1Field;
    private javax.swing.JTextField Num2Field;
    private javax.swing.JToggleButton NumberButton;
    private javax.swing.JButton OkButton;
    private javax.swing.JTextField OperatorField;
    private javax.swing.JLabel Pizza;
    private javax.swing.JTextField Result;
    private javax.swing.JPanel SaveLabels;
    private javax.swing.JPanel SavePanel;
    private javax.swing.JToggleButton ShiftButtton;
    private javax.swing.JPanel TextFields;
    private javax.swing.JPanel TopCentre;
    private javax.swing.JPanel TopLeft;
    private javax.swing.JPanel TopRight;
    private javax.swing.JLabel ZoomImage;
    private javax.swing.JLabel callingLabel;
    private javax.swing.JTextField firstfield;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField lastfield;
    private javax.swing.JTextField numberfield;
    // End of variables declaration//GEN-END:variables
}
