package Utils;


import java.io.*;

//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtils {

	
	 private static XSSFSheet ExcelWSheet;
	   private static XSSFWorkbook ExcelWBook;
	   private static XSSFCell Cell;
	   private static XSSFRow Row;
		
	   public static Object[][] getTableArray(String FilePath, String SheetName, int cols) throws Exception {   
		   
		    String[][] tabArray = null;
		 
		    try {
		 
		    FileInputStream ExcelFile = new FileInputStream(FilePath);
		 
		    // Access the required test data sheet
		 
		    ExcelWBook = new XSSFWorkbook(ExcelFile);
		 
		    ExcelWSheet = ExcelWBook.getSheet(SheetName);
		 
		    int startRow = 1;
		 
		    int startCol = 0;
		 
		    int ci,cj;
		 
		    int totalRows =  ExcelWSheet.getPhysicalNumberOfRows();
		 
		    // you can write a function as well to get Column count
		 
		    int totalCols = cols;
		 
		    tabArray=new String[totalRows-1][totalCols];
		 
		    ci=0;
		 
		    for (int i=startRow;i<totalRows;i++, ci++) {           	   
		 
		   cj=0;
		 
		    for (int j=startCol;j<totalCols;j++, cj++){
		 
		    tabArray[ci][cj]=getCellData(i,j);
		 
		    System.out.println(tabArray[ci][cj]);  
		 
		 }
		 
		 }
		 
		 }
		 
		 catch (FileNotFoundException e){
		 
		 System.out.println("Could not read the Excel sheet");
		 
		 e.printStackTrace();
		 
		 }
		 
		 catch (IOException e){
		 
		 System.out.println("Could not read the Excel sheet");
		 
		 e.printStackTrace();
		 
		 }
		 
		 return(tabArray);
		 
		 }
	   public static String getCellData(int RowNum, int ColNum) throws Exception {

			try{

				Cell =  ExcelWSheet.getRow(RowNum).getCell(ColNum);
				if (Cell==null)
					return "";
				
				
				int dataType = Cell.getCellType().getCode();

				if  (dataType == 3) {

					return "";

				}else{

					String CellData = Cell.getStringCellValue();

					return CellData;

				}}catch (Exception e){

				System.out.println(e.getMessage());

				throw (e);

				}

			

	}
	   //Constructor to connect to the Excel with sheetname and Path
	   public ExcelUtils(String Path, String SheetName) throws Exception {
	   
	      try {
	    	  
	    	// Open the Excel file
		         FileInputStream ExcelFile = new FileInputStream(Path);
		         
	    	  
	    	  String fileExtensionName = Path.substring(Path.indexOf(".")); 
	    	//Check condition if the file is xlsx file 
	    	//if(fileExtensionName.equals(".xlsx")){ //If it is xlsx file then create object of XSSFWorkbook class 
	    		ExcelWBook = new XSSFWorkbook(ExcelFile); 
	    	//} 
	    	//Check condition if the file is xls file 
	    	//else if(fileExtensionName.equals(".xls")){ //If it is xls file then create object of XSSFWorkbook class 
	    		//ExcelWBook = new HSSFWorkbook(ExcelFile); 
	    	//}
	    	  
	    	  
	    	  
	    	  
	    	  
	         
	         // Access the required test data sheet
	        // ExcelWBook = new XSSFWorkbook(ExcelFile);
	         ExcelWSheet = ExcelWBook.getSheet(SheetName);
	      } catch (Exception e) {
	         throw (e);
	      }
	   }
	      
	   //This method is to set the rowcount of the excel.
	   public int excel_get_rows() throws Exception {
	   
	      try {
	    	  return ExcelWSheet.getLastRowNum();
	      //   return ExcelWSheet.getPhysicalNumberOfRows();
	      } catch (Exception e) {
	         throw (e);
	      }
	   }
	   
	   //This method to get the data and get the value as strings.
	   public String getCellDataasstring(int RowNum, int ColNum) throws Exception {
	   
	      try {
	    		 String CellData ="";
	    			        
	    	  if(!(ExcelWSheet.getRow(RowNum).getCell(ColNum)==null)) {
	        	 CellData = ExcelWSheet.getRow(RowNum).getCell(ColNum).getStringCellValue();
	         }
	         
	         System.out.println("The value of CellData " + CellData);
	         return CellData;	         
	         
	      } catch (Exception e) {
	         return "";
	      }
	   }
	   
	   //This method to get the data and get the value as number.
	   public int getCellDataasnumber(int RowNum, int ColNum) throws Exception {
	   
	      try {
	         int CellData =
	            (int) ExcelWSheet.getRow(RowNum).getCell(ColNum).getNumericCellValue();
	         System.out.println("The value of CellData " + CellData);
	         return CellData;
	      } catch (Exception e) {
	         return 0;
	      }
	   }
}