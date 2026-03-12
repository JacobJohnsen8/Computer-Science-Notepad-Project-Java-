import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
// Extra Credit Project made by Jacob Johnsen for CS:2230
public class ExtraCreditProject extends JFrame implements KeyListener, ActionListener {

    private final JTextArea textArea;
    private final CustomStack<String> undoStack = new CustomStack<>(10);
    private final CustomStack<String> redoStack = new CustomStack<>(10);

    /**
     * Default constructor to setup the screen.
     * Do not edit this method.
     **/
    public ExtraCreditProject() {
        setTitle("Data Structures Awesome Editor");
        textArea = new JTextArea();

        JMenuBar menuBar = new JMenuBar();
        JMenu menuItems = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem quitItem = new JMenuItem("Quit");

        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        quitItem.addActionListener(this);

        menuItems.add(newItem);
        menuItems.add(openItem);
        menuItems.add(saveItem);
        menuItems.add(quitItem);

        JMenu doStuffMenu = new JMenu("Actions");

        JMenuItem undoItem = new JMenuItem("Undo");
        JMenuItem redoItem = new JMenuItem("Redo");
        JMenuItem solveMathItem = new JMenuItem("Solve Math");
        JMenuItem spellCheckItem = new JMenuItem("Spell Check");

        undoItem.addActionListener(this);
        redoItem.addActionListener(this);
        solveMathItem.addActionListener(this);
        spellCheckItem.addActionListener(this);

        doStuffMenu.add(undoItem);
        doStuffMenu.add(redoItem);
        doStuffMenu.add(solveMathItem);
        doStuffMenu.add(spellCheckItem);

        menuBar.add(menuItems);
        menuBar.add(doStuffMenu);

        setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        textArea.addKeyListener(this);
        add(new JScrollPane(textArea), BorderLayout.CENTER); // Add scroll pane for better text area handling
        setSize(600, 600);
        setVisible(true);
    }

    /**
     * This method will prompt the user to choose a folder to save a file.
     * Do not edit this method.
     **/
    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser("Save");
        int answer = fileChooser.showSaveDialog(this);
        if (answer == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
                output.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "File Saved.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Canceled Save Request.");
        }
    }

    /**
     * This method will prompt the user to choose a file to open.
     * Do not edit this method.
     **/
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser("Open");
        int answer = fileChooser.showOpenDialog(this);
        if (answer == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            StringBuilder outputText = new StringBuilder();
            try (BufferedReader fin = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = fin.readLine()) != null) {
                    outputText.append(line).append("\n");
                }
                textArea.setText(outputText.toString());
                JOptionPane.showMessageDialog(this, "Successfully opened file.");
            } catch (IOException evt) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + evt.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Cancelled file opening.");
        }
    }

    /**
     * Not used in this project but needed because of the interface.
     **/
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Not used in this project but needed because of the interface.
     **/
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Method to handle keystrokes.
     **/
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isWhitespace(c) || !Character.isISOControl(c)) {
            pushToUndoStack(textArea.getText());//Any character or white space is pushed to the undo stack
            redoStack.clear(); // Clear redo stack after any new edit
        }
        System.out.println("typed:" + e + "\n");
    }

    /**
     * If a menu option is chosen, do something...
     * New, Save, Open and Quit are all functional and should not be changed.
     * Read the above code and figure out how to add methods to accomplish the
     * tasks in the "Actions" menu bar. In other words, complete the code such
     * that undo, redo, solve math and spell check all work.
     **/
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println(action);
        switch (action) {
            case "New":
                textArea.setText(""); // creating a new document, simple, clear text.
                break;
            case "Save":
                saveFile(); // saving a file, done in another method
                break;
            case "Open":
                openFile(); // opening a file, similar to saving one
                break;
            case "Quit":
                System.exit(0);
                break;
            case "Undo":
                performUndo();
                break;
            case "Redo":
                performRedo();
                break;
            case "Solve Math":
                solveMath();
                break;
            case "Spell Check":
                spellCheck();
                break;
            // Implement additional actions for Undo, Redo, Solve Math, and Spell Check here.
        }
    }

    //Helper method to push onto undo stack
    private void pushToUndoStack(String state) {
        undoStack.push(state);
    }

    //Helper method to push onto redo stack
    private void pushToRedoStack(String state) {
        redoStack.push(state);
    }

    /*Undo method That takes the top of the undo stack and replaces the last thing in the text area
    with it or if the undo stack is empty it displays that message in the text box
     */
    private void performUndo() {
        if (!undoStack.isEmpty()) {
            String currentState = textArea.getText();
            pushToRedoStack(currentState);
            textArea.setText(undoStack.pop());
        } else {
            JOptionPane.showMessageDialog(this, "Nothing to Undo!");
        }
    }

    /*Redo method That takes the top of the redo stack and replaces the last thing that was undone in the text area
    with it or if the redo stack is empty it displays that message in the text box
     */
    private void performRedo() {
        if (!redoStack.isEmpty()) {
            String currentState = textArea.getText();
            pushToUndoStack(currentState);
            textArea.setText(redoStack.pop());
        } else {
            JOptionPane.showMessageDialog(this, "Nothing to Redo!");
        }
    }

    /* math Method that finds the equations in the text box that are between the $$$ and then uses the helper
    methods I made to replace what is in them with the answer to the equations
    */
    public void solveMath() {
        String text = textArea.getText();
        StringBuilder updatedText = new StringBuilder();
        int start = 0;

        while (start < text.length()) {
            int openIndex = text.indexOf("$$$", start);
            if (openIndex == -1) {
                // No more $$$ found; append the rest of the text
                updatedText.append(text.substring(start));
                break;
            }

            // Append text before $$$
            updatedText.append(text, start, openIndex);

            // Find the closing $$$
            int closeIndex = text.indexOf("$$$", openIndex + 3);
            if (closeIndex == -1) {
                // No closing $$$; append the rest of the text and break
                updatedText.append(text.substring(openIndex));
                break;
            }

            // Extract the inner expression between $$$...$$$
            String innerExpression = text.substring(openIndex + 3, closeIndex);
            try {
                // Evaluate the inner expression
                int result = evaluateExpression(innerExpression);
                // Replace the expression with its result
                updatedText.append(result);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error solving equation: " + e.getMessage());
                // Keep the original $$$...$$$ if there's an error
                updatedText.append("$$$").append(innerExpression).append("$$$");
            }

            // Move the start index past the closing $$$
            start = closeIndex + 3;
        }

        // Update the text area with the modified text
        textArea.setText(updatedText.toString());
    }

    /*Helper Method That takes the equation that was inside the $$$ and checks for variables inside the $$$
     and then replaces the variables in the equation part with what the variables are equal to. This uses my custom
     map in order to store the variables and their corresponding value */
    private int evaluateExpression(String expression) throws Exception {
        CustomMap<String, Integer> variables = new CustomMap<>(20); // Small size for simplicity
        String[] parts = expression.split(":");

        // If variables are present, parse them
        if (parts.length == 2) {
            String variablePart = parts[0];
            String[] variableAssignments = variablePart.split(",");
            for (String assignment : variableAssignments) {
                String[] varParts = assignment.split("=");
                if (varParts.length != 2) {
                    throw new Exception("Invalid variable assignment: " + assignment);
                }
                String varName = varParts[0].trim();
                int varValue = Integer.parseInt(varParts[1].trim());
                variables.put(varName, varValue);
            }
            expression = parts[1]; // The actual equation part
        }

        // Replace variables in the equation
        for (int i = 0; i < variables.capacity; i++) {
            CustomMap.Entry<String, Integer> entry = variables.table[i];
            //Iterates through the custom map's underlying table to retrieve variables.
            if (entry != null) {
                expression = expression.replaceAll("\\b" + entry.key + "\\b", String.valueOf(entry.value));
            }/*Replaces occurrences of variable names in the expression with their values. The \\b ensures
            full-word matches.*/
        }

        // Evaluate the mathematical expression by Delegating the actual math computation to evaluateMathExpression method
        return evaluateMathExpression(expression);
    }

    /*This Helper method actually solves the math by finding the values and operators in the equation Iterates
    through the expression character by character.Whitespace is Ignored. Builds multi-digit numbers and pushes
    them onto the values stack. Pushes opening parentheses to the operators stack; for closing parentheses
    evaluate until an opening parenthesis is encountered. Operators: Handles precedence using the precedence method
    and applies operators as needed.*/
    private int evaluateMathExpression(String expression) throws Exception {
        CustomStack<Integer> values = new CustomStack<>(100);
        CustomStack<Character> operators = new CustomStack<>(100);

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isWhitespace(c)) continue;

            if (Character.isDigit(c)) {
                int num = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                i--;
                values.push(num);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // Remove '('
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    //Helper that just finds out which operator is which
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    //Helper that makes sure Pemdas/precedence is followed
    private int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    //Helper method that does the math by applying the operators to each number
    private int applyOperator(char op, int b, int a) throws Exception {
        return switch (op) { //The switch statement maps operators directly to their respective operations.

            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) throw new Exception("Division by zero");
                yield a / b;
            }
            default -> throw new Exception("Unknown operator: " + op);
        };
    }

    //Method to Compare words in the text box to words from the dictionary
    public void spellCheck() {
        try {
            System.out.println("Loading dictionary...");
            CustomSet dictionary = loadDictionary();
            System.out.println("Dictionary loaded successfully!");

            String[] words = textArea.getText().split("\\s+");
            StringBuilder updatedText = new StringBuilder();

            for (String word : words) {
                System.out.println("Checking word: " + word);
                String cleanedWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                if (!dictionary.contains(cleanedWord)) {
                    System.out.println("Word not found in dictionary: " + cleanedWord);
                    String correctedWord = suggestCorrection(word, dictionary);
                    updatedText.append(correctedWord).append(" ");
                } else {
                    System.out.println("Word is correct: " + word);
                    updatedText.append(word).append(" ");
                }
            }

            textArea.setText(updatedText.toString().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error in Spell Check: " + e.getMessage());
        }
    }

    //Loads the entire dictionary into my custom set
    private CustomSet loadDictionary() throws IOException {
        CustomSet dictionary = new CustomSet();
        System.out.println(1);
        File file = new File("dictionary.txt");
        System.out.println(2);
        if (!file.exists()) {
            throw new FileNotFoundException("Dictionary file not found: dictionary.txt");
        }
        System.out.println(3);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line.trim().toLowerCase());
            }
        }
        return dictionary;
    }

    private String suggestCorrection(String word, CustomSet dictionary) {
        CustomList<String> suggestions = new CustomList<>();
        String cleanedWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

        // Generate suggestions
        if (cleanedWord.isEmpty()) {
            return word;
        }
        addOneLetter(cleanedWord, suggestions, dictionary);
        removeOneLetter(cleanedWord, suggestions, dictionary);
        swapLetters(cleanedWord, suggestions, dictionary);
        suggestions.add(word);

        // If no suggestions, keep the original word
        if (suggestions.isEmpty()) {
            return word;
        }

        // Show suggestions in a dialog
        String correctedWord = (String) JOptionPane.showInputDialog(
                this,
                "Select a correction for: " + word,
                "Spell Check",
                JOptionPane.QUESTION_MESSAGE,
                null,
                suggestions.toArray(),
                suggestions.get(0)
        );

        return correctedWord != null ? correctedWord : word; // Replace or keep original
    }

    private void addOneLetter(String word, CustomList<String> suggestions, CustomSet dictionary) {
        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String newWord = word.substring(0, i) + c + word.substring(i);
                if (dictionary.contains(newWord) && !suggestions.contains(newWord)) {
                    suggestions.add(newWord);
                }
            }
        }
    }

    private void removeOneLetter(String word, CustomList<String> suggestions, CustomSet dictionary) {
        for (int i = 0; i < word.length(); i++) {
            String newWord = word.substring(0, i) + word.substring(i + 1);
            if (dictionary.contains(newWord) && !suggestions.contains(newWord)) {
                suggestions.add(newWord);
            }
        }
    }

    private void swapLetters(String word, CustomList<String> suggestions, CustomSet dictionary) {
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            // Swap letters
            char temp = chars[i];
            chars[i] = chars[i + 1];
            chars[i + 1] = temp;

            String newWord = new String(chars);
            if (dictionary.contains(newWord) && !suggestions.contains(newWord)) {
                suggestions.add(newWord);
            }

            // Swap back
            chars[i + 1] = chars[i];
            chars[i] = temp;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExtraCreditProject::new); // Start the GUI on the Event Dispatch Thread
    }
}
