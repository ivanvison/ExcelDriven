package ivmaventest.ExcelDriven;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDriven {

	//public static void main(String[] args) throws IOException {

	
	//}
	
	
	//Method that get testcases
	public ArrayList<String> getData(String testCaseName) throws IOException {
		ArrayList<String> sampleData = new ArrayList<String>();
		
		//fileInputStream argument
		FileInputStream file = new FileInputStream(".//Excel_Testdata.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file); 
		
		//Go to sheet 1
		int numSheets = workbook.getNumberOfSheets();
	
		for (int i = 0; i < numSheets; i++) {
			
			if(workbook.getSheetName(i).equalsIgnoreCase("Testdata")) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				
				//1 - identify Test cases column by scanning entire row
				Iterator<Row> rows = sheet.iterator(); // Sheet is collection of rows
				Row firstRow = rows.next();
				Iterator<Cell> cell = firstRow.iterator(); // First Row is collection of cells
				
				int k=0;
				int column = 0;
				
				while(cell.hasNext()) {
					Cell value = cell.next();
					if(value.getStringCellValue().equalsIgnoreCase("Testcases")) {
						//Desired column
						column = k;
					}
					
					k++;
				}
				System.out.println(column);
				
				//2 - Once identified the Test Cases column, identify Test case row
				while(rows.hasNext()) {
					Row r = rows.next();
					if(r.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
						//Desired Row
						//3 - After grabbing purchase test cases row, pull the data and feed into test
						Iterator<Cell> cv = r.cellIterator();
						
						while(cv.hasNext()) {
							Cell c = cv.next();
							if(c.getCellType()==CellType.STRING) {
								sampleData.add(c.getStringCellValue());
							}
							else {
								sampleData.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
						}
						
						
					}
				}
			}
			
		}
		return sampleData;
	}
	

}
