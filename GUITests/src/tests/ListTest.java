package tests;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ListTest {

  public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setText("List Example");
    shell.setSize(300, 200);

    final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
    list.setBounds(40, 20, 220, 100);
    for (int loopIndex = 0; loopIndex < 9; loopIndex++) {
      list.add("Item Number " + loopIndex);
    }

    final Text text = new Text(shell, SWT.BORDER);
    text.setBounds(60, 130, 160, 25);

    list.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent event) {
        int[] selectedItems = list.getSelectionIndices();
        String outString = "";
        for (int loopIndex = 0; loopIndex < selectedItems.length; loopIndex++)
          outString += selectedItems[loopIndex] + " ";
        text.setText("Selected Items: " + outString);
      }

      public void widgetDefaultSelected(SelectionEvent event) {
        int[] selectedItems = list.getSelectionIndices();
        String outString = "";
        for (int loopIndex = 0; loopIndex < selectedItems.length; loopIndex++)
          outString += selectedItems[loopIndex] + " ";
        System.out.println("Selected Items: " + outString);
      }
    });

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }
}

