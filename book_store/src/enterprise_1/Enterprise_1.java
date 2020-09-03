
package enterprise_1;

/**
 *
 Name:  Lucas
 * Course:CNT 4714–Summer2020
 * Assignment title: Project1 –Event-driven Enterprise Simulation
 * Date: Sunday May 31, 2020
 */
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.*;



public class Enterprise_1 implements ActionListener  {
     
   private static final int WINDOW_WIDTH = 700; //pixels
   private static final int WINDOW_HEIGHT = 220; //pixels
   private static final int FIELD_WIDTH = 30;   //characters
   private static final int AREA_WIDTH = 40;   //characters
   
   //member variables
   static int ITEM = 1;
   static int order_size=10000;
   int index=-1;
   static boolean flag_book=false;
    ArrayList<ArrayList<Object> > cart =  
                  new ArrayList<ArrayList<Object>>();    
    int quantity;
    int book_id1;
    
    double total=0;
    double PRICE=0;
    
   
   
   
   
   private static final FlowLayout LAYOUT_STYLE = new FlowLayout();

   private static final String LEGEND = "Shop Stuff ";     
   //instance variables
   //window for GUI
   private JFrame window = new JFrame("Book Shop");
   

   

   //user entry area for order
   private JLabel item_label = new JLabel("                 Enter Number of Items in this order:");
   private JTextField item_label_text = new JTextField(FIELD_WIDTH);

   private JLabel book_id = new JLabel("                                       Enter Book ID for Item "+ITEM+":");
   private JTextField book_id_text = new JTextField(FIELD_WIDTH);
   
   private JLabel amount = new JLabel("                                     Enter quantity for item "+ITEM+":");
   private JTextField amount_text = new JTextField(FIELD_WIDTH);
 
   private JLabel info = new JLabel("                                                             Item #"+ITEM+" info");
   private JTextField info_text = new JTextField(FIELD_WIDTH);
   
   private JLabel subtotal = new JLabel("                                                              Order subtotal for "+(ITEM-1)+" Itens");
   private JTextField subtotal_text = new JTextField(FIELD_WIDTH);
   

   //buttons
   private JButton process_button = new JButton("  Process Item #"+ITEM);
   private JButton new_order_button = new JButton("New Order");
   private JButton exit_button = new JButton("Exit");
   private JButton confirm_button = new JButton("Confirm Item #"+ITEM);
   private JButton finish_button = new JButton("Finish Order");
   private JButton view_button = new JButton("View Order");
  
   
    int get_discount(int n)
       {
           int discount=0;
        
           if(n<4)
                discount=0;
           if(n<10&&n>3)
                discount=10;
           if(n<14&&n>9)
                discount=15;
           if(n>15)
                discount=20;
           
        return discount;
        
       }
    double calculate_price(int discount,double price)
    {
        Double k =new Double(discount);
        
        return price*(1-k/100);
        
    }
    
    void reset_all_data()
    {
    
               //disable buttons
               view_button.setEnabled(false);
               finish_button.setEnabled(false);
               confirm_button.setEnabled(false);
               
               
               //clear data
               ITEM=1;
               order_size=1000;
               flag_book=false;
               cart.clear();
               index=-1;
               total=0;
               PRICE=0;
               
               
               // clear text fields
               item_label_text.setText(""); 
               book_id_text.setText("");
               amount_text.setText("");
               subtotal_text.setText("");
               info_text.setText("");
               
               //clear buttons
                process_button.setText("Process Item "+"#"+ITEM);
                confirm_button.setText("Confirm Item "+"#"+ITEM);
                
                //clear labels
                book_id.setText("                                       Enter Book ID for Item "+ITEM+":");
                amount.setText("                                     Enter quantity for item "+ITEM+":");
                info.setText("                                                             Item #"+ITEM+" info");
                subtotal.setText("                                                              Order subtotal for "+(ITEM-1)+" Itens");        
                   
    
    }
   
    
    void create_transaction()
    {
        String dir = System.getProperty("user.dir");        
    try {
      File file = new File(dir+"\\src\\enterprise_1\\transactions.txt");
      file.createNewFile();
    } catch (IOException e) {
      
      e.printStackTrace();
    }
    return;
    }
    
    void write_to_transaction(String s)
    {
        String dir = System.getProperty("user.dir");
    try {
      FileWriter myWriter = new FileWriter(dir+"\\src\\enterprise_1\\transactions.txt",true);
      
      myWriter.write(s);
      myWriter.close();
      
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    
    return;
    }
    
    void store_transaction(ArrayList<Object> basket)
    {
         int id=(int)basket.get(0);
                   int amoun=(int)basket.get(1);
                   Object name=basket.get(2);
                   double price_unit=(double)basket.get(3);
                   double price=price_unit*amoun;
                   int discount=get_discount(amoun);
                   calculate_price(discount, price);
                   String price_string=String.format("%.2f", price);
                   
                    SimpleDateFormat fo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    SimpleDateFormat pin = new SimpleDateFormat("ddMMyyyyHHmmss");
                    Date date = new Date();
                    
                     String time_pin = pin.format(date); 
                     String str_time = fo.format(date); 
                   
                   String output=time_pin+", "+id+", "+name+", $"+price_unit+", "+amoun
                           +", "+discount+"%"+ ", $"+price_string+", "+str_time
                           +"\n";
                   
                   
                   write_to_transaction(output);
        
    return;
    }
    
    void delete_transactions()
    {
         String dir = System.getProperty("user.dir");
         File file = new File(dir+"\\src\\enterprise_1\\transactions.txt");
         file.delete();
    
    }
    
    void prepare_invoice()
    {
        String final_str;
        
        int itens=ITEM;
        
        Date date = new Date();
        SimpleDateFormat fo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String str_time = fo.format(date);
        
        
        String str1 = "Number of Items: ";
        String str2 = "Item#/ ID / Title / Price / Qty / Disc% / Subtotal: ";
        String str3 = print_cart();
        String str4 = "Order subtotal: ";
        String str5 = "Tax rate ";
        String str6 = "Tax amount ";
        String str7 = "Order Total ";
        String str8 = "Thank you for shopping at: The Magic Book Shop!\n "
                + "Hopefully we will see you again!";
        
        
        String total_str = String.format("%.2f", total);
        double real_total_n=1.06*total;
        String real_total = String.format("%.2f", real_total_n);
        double taxes_n=total*0.06;
        String taxes = String.format("%.2f", taxes_n);
        
        String.format("%.2f", total);
        final_str=str_time+"\n\n"+
                str1+itens+"\n\n"+
                str2+"   "+"\n\n"+
                str3+"   "+"\n\n"+
                str4+"   "+total_str+"\n\n"+
                str5+"   6%"+"\n\n"+
                str6+"   $"+taxes+"\n\n"+
                str7+"   $"+real_total+"\n\n"+
                str8+"\n\n";
        
        JOptionPane.showMessageDialog(null,final_str);
                
        
    }
    
   String print_cart()
    {        
    String output="";
        int counter=1;
               for (ArrayList<Object> basket :cart)
               {
                   
                   int id=(int)basket.get(0);
                   int amoun=(int)basket.get(1);
                   Object name=basket.get(2);
                   double price_unit=(double)basket.get(3);
                   int discount=get_discount(amoun);                   
                   double price=price_unit*amoun;
                   price=calculate_price(discount,price);
                   String price_string=String.format("%.2f", price);
                   
                   
                   output=output+counter+". "+id+" "+name+" $"+price_unit+" "+amoun
                           +" "+discount+"%"+ " $"+price_string
                           +"\n";                  
                   counter++;
               }
       return output;        
    }
   
   
   public Enterprise_1() throws FileNotFoundException{
       // variable used to add stuff to cart
      
       
           //configure GUI
       window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
       subtotal_text.setEditable(false);
      
        
       //register event listener       
       process_button.setOpaque(true);
       
  
       //add components to the container
       Container c = window.getContentPane();
       c.setLayout(LAYOUT_STYLE);

       
       // disable buttons in the beginning
       confirm_button.setEnabled(false);
       view_button.setEnabled(false);
       finish_button.setEnabled(false);
       
       
       //put the file in array So it is fast to work with
       ArrayList<Long> id_data = new ArrayList<Long>();
       ArrayList<String> title_data = new ArrayList<String>();
       ArrayList<Double> price_data= new ArrayList<Double>();
      
       
       //add action listener to buttons
            // process button
       process_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            
               PRICE=0;
                
               // disbles 2 buttons 
               confirm_button.setEnabled(true);
               process_button.setEnabled(false);   
      
               //get information
               if(ITEM==1)
               {
                    String response1 = item_label_text.getText();
                    order_size  = Integer.parseInt(response1);
                    create_transaction();
               }
               
               
               String response2 = book_id_text.getText();
                book_id1 = Integer.parseInt(response2);
               String response3 = amount_text.getText();
                 quantity= Integer.parseInt(response3);
               
                              
               int i=0;
               for(Long pid:id_data) 
               {                  
                   if(pid==book_id1)
                   {
                       flag_book=true;
                       index=i;
                   }
                   i++;                  
               }
               if(flag_book)
               {
                int disc=get_discount(quantity);   
                double p=price_data.get(index)*quantity;                
                double price=calculate_price(disc,p);
                PRICE=price;
                String price_string=String.format("%.2f", price);
                
                String output = String.valueOf(id_data.get(index));
                output=output+" "+title_data.get(index)+" $"+price_data.get(index)
                        +" "+quantity+" "+disc+" $"+price_string;
                info_text.setText(output);                        
               }
               else
               {

                   info_text.setText("Book ID not found");
               }               
           }           
           }  
       );
       
       
       confirm_button.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               
               
               process_button.setEnabled(true);
               
               if(ITEM == order_size)
               {
                   process_button.setEnabled(false);
               }
               
               
               if(flag_book)
               {   
                   if(ITEM == 1)
                   {
                       view_button.setEnabled(true);
                       finish_button.setEnabled(true);
                   }
                   
                   ArrayList<Object> c1 =new ArrayList<Object>();
                   flag_book=false;                 
                   c1.add(book_id1);
                   c1.add(quantity);
                   c1.add(title_data.get(index));
                   c1.add(price_data.get(index));
                   cart.add(c1);
                   
                   // big func ahead
                   store_transaction(c1);
                   
                   String output="Item #"+ITEM+" accepted";
                   JOptionPane.showMessageDialog(null,output);
                   ITEM++;
                   // update button text
                   process_button.setText("Process Item "+"#"+ITEM);
                   confirm_button.setText("Confirm Item "+"#"+ITEM);
                   
                   //update other text
                   book_id.setText("                                       Enter Book ID for Item "+ITEM+":");
                   amount.setText("                                     Enter quantity for item "+ITEM+":");
                   info.setText("                                                             Item #"+ITEM+" info");
                   subtotal.setText("                                                              Order subtotal for "+(ITEM-1)+" Itens");        
                   
                   total=total+PRICE;
                   String total_string=String.format("%.2f", total);
                   subtotal_text.setText("$"+total_string);
                   
                   
               }
               else
               {
                   String output="Book id: " + book_id1 + " Invalid";
                   JOptionPane.showMessageDialog(null,output);
                   
               }
               
                
               book_id1=-1;
               quantity=-1;
               index=-1;
              
               
               confirm_button.setEnabled(false);
              
           }           
           }  
       );
       
       view_button.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               
               String output=print_cart();
               
               JOptionPane.showMessageDialog(null,output);
                
           }
           
           }  
       );
       finish_button.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               process_button.setEnabled(false);        
               confirm_button.setEnabled(false);
               prepare_invoice();
             
               
           }
           
           }  
       );
       
       new_order_button.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               process_button.setEnabled(true);        
               confirm_button.setEnabled(true);        
               reset_all_data();
               //delete_transactions();
               
           }
           
           }  
       );
       
       exit_button.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
            System.exit(0);
            delete_transactions();
            
           }
           
           }  
       );
       
       
       
       /// add to framework
       c.add(item_label);
       c.add(item_label_text);
       c.add(book_id);
       c.add(book_id_text);
       c.add(amount);
       c.add(amount_text);
       c.add(info);
       c.add(info_text);
       c.add(subtotal);
       c.add(subtotal_text);
       c.add(process_button);      
       c.add(confirm_button);
       c.add(finish_button);
       c.add(view_button);
       c.add(new_order_button);
       c.add(exit_button);
       //display GUI
       window.show();
       
       
       //read file
       String dir = System.getProperty("user.dir");
       File file = new File(dir+"\\src\\enterprise_1\\inventory.txt");
       Scanner sc = new Scanner(file);      
       String[] split_str;
       
       while(sc.hasNext())
       {
           String str=sc.nextLine();
           split_str=str.split(",",0);
           
           id_data.add(Long.parseLong(split_str[0]));
           title_data.add(split_str[1]);
           price_data.add(Double.parseDouble(split_str[2]));
           
           
       }
      
      
    }
   // does the adtion of cost
   // calculate the discount
   public void actionPerformed(ActionEvent e) {
       //get user's responses
       
    }
         
    public static void main(String[] args) throws FileNotFoundException {
        
        Enterprise_1 gui = new Enterprise_1();
        
    }
    
}

