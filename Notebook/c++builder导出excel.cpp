//c++builder 如何导出数据到Excel  

#define PG OlePropertyGet
#define PS OlePropertySet
#define FN OleFunction
#define PR OleProcedure

 int i,j,k;
 String col,row,s_day;
 //String strXlsFileName="C:\\123.xls";
 s_day=DateToStr(Date());
 String strXlsFileName=GetCurrentDir()+"\\"+s_day+"ryxxcx.xls";
    String strCellValue;
    Variant ExcelApp,Workbook1,Sheet1,range1;
    int nSheetCount;
    //只保留两个文件ryxxcx.xls  bakryxxcx.xls
    if(FileExists(strXlsFileName))
    {
        if(Application->MessageBoxA("Excel文件已存在，是否覆盖？","注意",MB_YESNO)==IDYES)
        {
          DeleteFile(strXlsFileName);
        }
        else
        {
        //文件存在则删除
          String bak=GetCurrentDir()+"\\bakryxxcx.xls";
          if(FileExists(bak))
          {
           DeleteFile(bak);
          }
          strXlsFileName=GetCurrentDir()+"\\bakryxxcx.xls";
        }
    }
    try
    {
        ExcelApp=Variant::CreateObject("Excel.Application");
            //创建Excel对象
        ExcelApp.PG("WorkBooks").PR("Add");
        //ExcelApp.PG("WorkBooks").PR("Open",strXlsFileName.c_str());
        Workbook1=ExcelApp.PG("ActiveWorkBook");
            //打开Excel文件
        //nSheetCount=Workbook1.PG("Sheets").PG("Count");
        //ShowMessage("Sheet总数为：" + String(nSheetCount));
            //Excel文件的Sheet总数
        Workbook1.PG("Sheets",1).PR("Select");
            //选择工作表中第一个工作表
        Sheet1=Workbook1.PG("ActiveSheet");
        //设置excel身份证号的格式

        ExcelApp.PG("Columns",9).PS("NumberFormat","@");
        ExcelApp.PG("Columns",18).PS("NumberFormat","@");
        ExcelApp.PG("Columns",23).PS("NumberFormat","@");
        //ExcelApp.PG("Columns",1).PS("ColumnWidth",22);
            //设置第一列的列宽为22
        //strCellValue=Sheet1.PG("Cells",2,3).PG("Value");
            //读取第2行第3列单元格的值
        //strCellValue += "123456";
        Application->MessageBoxA("确认导出","确认",MB_OKCANCEL);
        //ShowMessage("点击ok开始导出数据!");

        Query1->Open();
        Query1->First();
        for(i=0;i<Query1->FieldCount;i++)//写入字段名
         {
           col=Query1->Fields->Fields[i]->DisplayLabel;
           //col=Query1->Fields->Fields[i]->FieldName;
           Sheet1.PG("Cells",1,i+1).PS("Value",col.c_str());
         }
         //写入数据
        //ShowMessage("写入数据!");
        Query1->First();
        j=1;
        while(!Query1->Eof)
             {
               j=j+1;
               for(k=0;k<Query1->FieldCount;k++)
               {
                 row=Query1->Fields->Fields[k]->AsString;
                 //row=Query1->Fields->Fields[k]->DisplayText;
                // row=Query1->Fields->Fields[k]->AsVariant;
                 Sheet1.PG("Cells",j,k+1).PS("Value",row.c_str());
                 //Sheet1.PG("Cells",j,k+1).PS("Value",row);
               }
               Query1->Next();
             }
            //改变第2行第4列单元格的值
        //Sheet1.PG("Cells").PG("Font").PS("Size",9);
            //设置字号为9，针对所有单元格
        //Sheet1.PG("Cells").PG("Font").PS("Name","宋体");
            //设置字体为宋体，针对所有单元格
        Sheet1.PG("Rows",1).PG("Font").PS("Bold",true);
        // Sheet1.PG("Columns",9).PG("Font").PS("Bold",true);
        //设置第一行的字体为粗字体
        //Sheet1.PG("Cells",1,1).PG("Font").PS("Bold",true);
            //设置第一行第一列单元格的字体为粗字体
        //Sheet1.PG("Range","B3:B4").PR("Merge");
            //合并B3,B4两个单元格
        //Sheet1.PG("Range","C5:D6").PR("Merge");
            //合并C5,C6,D5,D6四个单元格
        //Sheet1.PS("Name","Sheet的新名字");
            //设置Sheet的名字
        //ShowMessage(strXlsFileName);
        Workbook1.FN("SaveAs",strXlsFileName.c_str());
            //保存文件
        //ShowMessage("保存文件!");
        Sheet1=NULL;
        Workbook1.PR("Close");
        Workbook1=NULL;
        ExcelApp.PR("Quit");
            //退出Excel
        ExcelApp=NULL;
        ExcelApp=Unassigned;
        Workbook1=Unassigned;
        Sheet1=Unassigned;
        ShowMessage("数据成功导出!");

       /*  if(Form1->Open1->Execute())
        {
         Form1->Memo1->Lines->LoadFromFile(Form1->Open1->FileName);
         //LoadFromFile(Form1->Open1->FileName);
        } */
    }
    catch(...)
    {
        MessageBox(Handle,"请安装正版Excel软件！！！",Application->Title.c_str(),MB_OK|MB_ICONERROR);
    }