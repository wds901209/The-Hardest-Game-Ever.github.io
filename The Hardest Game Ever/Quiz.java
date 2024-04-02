import java.awt.event.*;
import java.awt.*;
import javax.swing.*;


public class Quiz implements ActionListener{    //implements ActionListener 就像你簽了一份合同，而這個合同的內容是要你做關於ActionListener的事 e.g.按鈕的點擊
	
	String[] questions = 	{
								"以下何者不符合王東森?",
								"以下何者為新竹特色?",
								"以下何者不屬於Greedy Apporach問題",
								"以下哪個Sort演算法的Avg.最快?"
							};
	String[][] options = 	{
								{"懶惰","認真努力","積極溝通","勇於挑戰"}, //對應到第一題的4個選項
								{"鳳梨","貢丸","肉圓","麻糬"},
								{"Huffman alog.","Minmum Spanning Tree","1/0 Knaqpsack Problem","Sollin's algo."},
								{"Bubble sort","Insertion sort","Quick sort","Selection sort"}
							};
	char[] answers = 		{
								'A',
								'B',
								'C',
								'C'
							};
	char guess;
	char answer;
	int index;
	int correct_guesses = 0;
	int total_questions = questions.length;
	int result;
	int seconds=10;
	
	JFrame frame = new JFrame();                   //視窗
	JTextField textfield = new JTextField();	 //單行文字
	JTextArea textarea = new JTextArea();	 //多行文字
	JButton buttonA = new JButton();  		 //按鈕
	JButton buttonB = new JButton();
	JButton buttonC = new JButton();
	JButton buttonD = new JButton();
	JLabel answer_labelA = new JLabel();  //顯示按鈕
	JLabel answer_labelB = new JLabel();
	JLabel answer_labelC = new JLabel();
	JLabel answer_labelD = new JLabel();
	JLabel time_label = new JLabel();	 //顯示計時器
	JLabel seconds_left = new JLabel();	 //縣市計時器上倒數的數字
	JTextField number_right = new JTextField(); //計算正確題數
	JTextField percentage = new JTextField();  //答對百分比(number_right/toatl_question)
	
	Timer timer = new Timer(1000, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			seconds--;
			seconds_left.setText(String.valueOf(seconds));
			if(seconds<=0) {
				displayAnswer();
			}
			}
		});
	public Quiz() {
		//視窗
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(650,650);
		frame.setTitle("The Hardest Game Ever");
		frame.getContentPane().setBackground(new Color(0xF808A87)); //getContentPane用來控制視窗內的東西
		frame.setLayout(null);     //用於控制按鈕位置, null則表示我想要每個按鈕都由自己決定位置
		frame.setResizable(false);
		ImageIcon image = new ImageIcon("shiba-icon.jpg");
		frame.setIconImage(image.getImage());
		//題數區域(Question1.2...)
		textfield.setBounds(0,0,650,50);  //(x,y,wieth,height) 畫面上半部(顯示題數及題目的地方)
		textfield.setBackground(new Color(0xF808A87)); 					//顯示畫面上半部顏色
		textfield.setForeground(new Color(25,255,0)); 					//該區字串的顏色
		textfield.setFont(new Font("Times New Roman",Font.BOLD,30)); 	//顯示畫面上半部的字體,粗度,size
		textfield.setBorder(BorderFactory.createBevelBorder(1)); 			//邊框
		textfield.setHorizontalAlignment(JTextField.CENTER); 					//讓文字在JTextField.CENTER
		textfield.setEditable(false); 											//user 是否可編輯
		//題目區域(what is ....?)
		textarea.setBounds(0,50,650,50);
		textarea.setLineWrap(true);  										//若字串超過範圍即自動換行
		textarea.setWrapStyleWord(true); 									//不會讓文字單獨在一行(增加readable)
		textarea.setBackground(new Color(0xF808A87)); 						//A選項背景顏色
		textarea.setForeground(new Color(25,255,0)); 						//A選項字串的顏色
		textarea.setFont(new Font("宋體",Font.BOLD,25));///
		textarea.setBorder(BorderFactory.createBevelBorder(1));
		textarea.setEditable(false);
		//A
		buttonA.setBounds(0,125,100,100);
		buttonA.setFont(new Font("Times New Roman",Font.BOLD,35));
		buttonA.setFocusable(false);    //只允使使用者用滑鼠點擊
		buttonA.addActionListener(this);          //當user點擊A按鈕時，相應的操作會在Quiz類的處理方法中執行
		buttonA.setText("A");
		//B
		buttonB.setBounds(0,225,100,100);
		buttonB.setFont(new Font("Times New Roman",Font.BOLD,35));
		buttonB.setFocusable(false);
		buttonB.addActionListener(this);
		buttonB.setText("B");
		//C
		buttonC.setBounds(0,325,100,100);
		buttonC.setFont(new Font("Times New Roman",Font.BOLD,35));
		buttonC.setFocusable(false);
		buttonC.addActionListener(this);
		buttonC.setText("C");
		//D
		buttonD.setBounds(0,425,100,100);
		buttonD.setFont(new Font("Times New Roman",Font.BOLD,35));
		buttonD.setFocusable(false);
		buttonD.addActionListener(this);
		buttonD.setText("D");
		//OPTION A
		answer_labelA.setBounds(125,125,500,100);
		answer_labelA.setBackground(new Color(50,50,50));
		answer_labelA.setForeground(new Color(25,255,0));
		answer_labelA.setFont(new Font("宋體",Font.PLAIN,35));
		//OPTION B
		answer_labelB.setBounds(125,225,500,100);
		answer_labelB.setBackground(new Color(50,50,50));
		answer_labelB.setForeground(new Color(25,255,0));
		answer_labelB.setFont(new Font("宋體",Font.PLAIN,35));
		//OPTION C
		answer_labelC.setBounds(125,325,500,100);
		answer_labelC.setBackground(new Color(50,50,50));
		answer_labelC.setForeground(new Color(25,255,0));
		answer_labelC.setFont(new Font("宋體",Font.PLAIN,35));
		//OPTION D
		answer_labelD.setBounds(125,425,500,100);
		answer_labelD.setBackground(new Color(50,50,50));
		answer_labelD.setForeground(new Color(25,255,0));
		answer_labelD.setFont(new Font("宋體",Font.PLAIN,35));
		//倒數計時器
		seconds_left.setBounds(535,510,100,100);
		seconds_left.setBackground(new Color(25,25,25));
		seconds_left.setForeground(new Color(255,0,0));
		seconds_left.setFont(new Font("Times New Roman",Font.BOLD,60));
		seconds_left.setBorder(BorderFactory.createBevelBorder(1));
		seconds_left.setOpaque(true);  				//使倒數計時器的背景沒"不透明"
		seconds_left.setHorizontalAlignment(JTextField.CENTER);
		seconds_left.setText(String.valueOf(seconds));
		//最後統計 答題正確數
		number_right.setBounds(225,225,200,100);
		number_right.setBackground(new Color(25,25,25));
		number_right.setForeground(new Color(25,255,0));
		number_right.setFont(new Font("Times New Roman",Font.BOLD,50));
		number_right.setBorder(BorderFactory.createBevelBorder(1));
		number_right.setHorizontalAlignment(JTextField.CENTER);
		number_right.setEditable(false);
		//答對題數百分比
		percentage.setBounds(225,325,200,100);
		percentage.setBackground(new Color(25,25,25));
		percentage.setForeground(new Color(25,255,0));
		percentage.setFont(new Font("Times New Roman",Font.BOLD,50));
		percentage.setBorder(BorderFactory.createBevelBorder(1));
		percentage.setHorizontalAlignment(JTextField.CENTER);
		percentage.setEditable(false);
		
		frame.add(seconds_left);
		frame.add(answer_labelA);
		frame.add(answer_labelB);
		frame.add(answer_labelC);
		frame.add(answer_labelD);
		frame.add(buttonA);
		frame.add(buttonB);
		frame.add(buttonC);
		frame.add(buttonD);
		frame.add(textarea);
		frame.add(textfield);
		frame.setVisible(true);
		
		nextQuestion();
	}
	public void nextQuestion() {
		
		if(index>=total_questions) {
			results();
		}
		else {
			textfield.setText("QUESTION" + (index + 1)); //第一題.第二題....(start to 0)
            textarea.setText(questions[index]);          //顯示題目
            answer_labelA.setText(options[index][0]); //選項a
            answer_labelB.setText(options[index][1]); //選項b
            answer_labelC.setText(options[index][2]);
            answer_labelD.setText(options[index][3]);
            timer.start();
		}
	}
	@Override
    public void actionPerformed(ActionEvent e){ 		//操作的方法
        
        buttonA.setEnabled(false); 		//按了其中一個選項就不能按其他的
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);

        if(e.getSource() == buttonA){ 		//e.getSource()代表user按了A選項
            answer = 'A';                   //user選擇A (char)
            if(answer == answers[index]){  //用來確定各題分別的答案是否和user的答案一樣
                correct_guesses++;
            }
        }
        if(e.getSource() == buttonB){
            answer = 'B';
            if(answer == answers[index]){
                correct_guesses++;
            }
        }
        if(e.getSource() == buttonC){
            answer = 'C';
            if(answer == answers[index]){
                correct_guesses++;
            }
        }
        if(e.getSource() == buttonD){
            answer = 'D';
            if(answer == answers[index]){
                correct_guesses++;
            }
        }
        displayAnswer();
    }
	public void displayAnswer() {
		
		timer.stop();
		
		buttonA.setEnabled(false);
		buttonB.setEnabled(false);
		buttonC.setEnabled(false);
		buttonD.setEnabled(false);

		if(answers[index] != 'A')
			answer_labelA.setForeground(new Color(255,0,0));
		if(answers[index] != 'B')
			answer_labelB.setForeground(new Color(255,0,0));
		if(answers[index] != 'C')
			answer_labelC.setForeground(new Color(255,0,0));
		if(answers[index] != 'D')
			answer_labelD.setForeground(new Color(255,0,0));
		//當user答完第一題後 讓時間暫停
		Timer pause = new Timer(2000, new ActionListener() {
			//接著覆寫，使答案變回去"green"
			@Override
			public void actionPerformed(ActionEvent e) {
				
				answer_labelA.setForeground(new Color(25,255,0));
				answer_labelB.setForeground(new Color(25,255,0));
				answer_labelC.setForeground(new Color(25,255,0));
				answer_labelD.setForeground(new Color(25,255,0));
				
				answer = '0'; //
				seconds = 10;
				seconds_left.setText(String.valueOf(seconds));
				//使按鈕重啟
				buttonA.setEnabled(true);
				buttonB.setEnabled(true);
				buttonC.setEnabled(true);
				buttonD.setEnabled(true);
				index++;
				nextQuestion();
			}
		});
		pause.start();
		pause.setRepeats(false); //使計時器只執行一次(在一題中)
	}
	public void results(){
		//最後結算畫面時不讓user點擊按鈕
		buttonA.setEnabled(false);
		buttonB.setEnabled(false);
		buttonC.setEnabled(false);
		buttonD.setEnabled(false);
		
		result = (int)((correct_guesses/(double)total_questions)*100);
		
		textfield.setText("RESULTS!");
		textarea.setText("");
		answer_labelA.setText("");
		answer_labelB.setText("");
		answer_labelC.setText("");
		answer_labelD.setText("");
		
		number_right.setText(correct_guesses+"/"+total_questions);
		percentage.setText(result + "%");
		
		frame.add(number_right);
		frame.add(percentage);		
	}
}
