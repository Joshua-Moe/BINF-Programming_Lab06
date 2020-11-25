import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class MyQuizAminoAcidsGUI extends JFrame
{
	
	private static final long serialVersionUID = 1L;
	
	
	private int correct = 0;
	private int wrong = 0;
	
	private JTextField totalCorrect = new JTextField();
	private JTextField totalWrong = new JTextField();
	
	
	//private JTextField aaTextField = new JTextField();
	private JTextField timeLeft = new JTextField();

	
	private JTextField question = new JTextField();
	private String quizQuestion = "default";
	private JTextField answer = new JTextField();

	private JButton startQuiz = new JButton("Start Quiz");
	private JButton cancel = new JButton("Cancel");
	private boolean isCanceled = false;
	private int sharedIndex;
	
	JPanel jpText = new JPanel();
	JLabel jl = new JLabel();
	
	public static String[] SHORT_NAMES = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };
		
	public static String[] FULL_NAMES = 
		{"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};
	
	
	//The Quiz method here	
	public int SetQuestion()
	{
		int qRange = SHORT_NAMES.length;
		Random r = new Random();
		int qIndex = r.nextInt(qRange);
		String currentQuestion = FULL_NAMES[qIndex];
		quizQuestion = currentQuestion;
		updateText();
		return qIndex;
	}
	
	
	
	// Slow action
	private class timeKeeper implements Runnable
	{
		public void run()
		{
			try
			{
				int countDown = 30;
				timeLeft.setText("" + countDown);
				Thread.sleep(1000);
				
				while (!isCanceled && countDown > 0)
				{
					countDown--;
					timeLeft.setText("" + countDown);
					Thread.sleep(1000);
				}
			}
			catch(Exception e)
			{
				//error
			}
			cancel.setEnabled(false);
			startQuiz.setEnabled(true);
			answer.setEnabled(false);
		}
	}
	
	
	
	private void updateText()
	{
		question.setText("" + quizQuestion);
		totalCorrect.setText("Correct: " + correct);
		totalWrong.setText("Wrong: " + wrong);
		validate();
	}
	
	private JPanel getTopPanel()
	{
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(0,3));
		top.add(totalCorrect);
		top.add(totalWrong);
		top.add(timeLeft);
		return top;
	}
	
	private JPanel getMiddlePanel()
	{
		JPanel middle = new JPanel();
		middle.setLayout(new GridLayout(2,1));
		middle.add(question);
		middle.add(jpText);
		return middle;
	}
	
	private JPanel getBottomPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(startQuiz);
		panel.add(cancel);
		return panel;
	}
	
	private class slowActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			answer.setText("   ");
			correct = 0;
			wrong = 0;
			updateText();
			sharedIndex = SetQuestion();
			if (isCanceled)
			{
				isCanceled = false;
			}
			cancel.setEnabled(true);
			startQuiz.setEnabled(false);
			answer.setEnabled(true);
			//timeLeft.setText("31");
			//updateText();
			new Thread(new timeKeeper()).start();
		}
	}
	
	
	public MyQuizAminoAcidsGUI()
	{
		super("Amino Acids Quiz");
		setLocationRelativeTo(null);
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		getContentPane().add(getMiddlePanel(), BorderLayout.CENTER);
		getContentPane().add(getBottomPanel(),BorderLayout.SOUTH);
		
		//getContentPane(jpText, BorderLayout.CENTER);
		jpText.add(answer);
		answer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String x = answer.getText();
				String aChar = "" + x.charAt(0);
				if (aChar.toUpperCase().equals(SHORT_NAMES[sharedIndex]))
				{
					correct++;
				}
				else
				{
					wrong++;
				}
				sharedIndex = SetQuestion();
				updateText();
			}
		});
		
		startQuiz.addActionListener(new slowActionListener());
		
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (isCanceled == true)
				{
					isCanceled = false;
				}
				else if (isCanceled == false)
				{
					isCanceled = true;
				}
			}
		});
		
		jpText.add(jl);
		//add(jpText);
		
		totalCorrect.setText("Correct: " + correct);
		totalWrong.setText("Wrong: " + wrong);
		
		timeLeft.setText("30");
		
		
		question.setText("" + quizQuestion);
		answer.setText("   ");
		updateText();
		setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		new MyQuizAminoAcidsGUI();
	}
}
