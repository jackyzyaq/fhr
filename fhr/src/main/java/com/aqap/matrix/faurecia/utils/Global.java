package com.aqap.matrix.faurecia.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.yq.authority.pojo.MenuInfo;
import com.yq.authority.pojo.UserInfo;
import com.yq.faurecia.pojo.AnnualLeave;
import com.yq.faurecia.pojo.DepartmentInfo;
import com.yq.faurecia.pojo.EmployeeCard;
import com.yq.faurecia.pojo.EmployeeInfo;
import com.yq.faurecia.pojo.PositionInfo;

public class Global {

   public static final String DATE_FORMAT_STR_A = "yyyy-MM-dd";
   public static final String DATE_FORMAT_STR_B = "yyyy-MM-dd HH:mm:ss";
   public static final String DATE_FORMAT_STR_C = "yyyyMMdd";
   public static final String DATE_FORMAT_STR_D = "yyyy-MM";
   public static final String DATE_FORMAT_STR_E = "HH:mm";
   public static final String HOME = System.getProperty("user.dir");
   public static final String LOG_FILE_PROPERTIES = "log4j.properties";
   public static final String CONFIG_FILE_PROPERTIES = "config.properties";
   public static final String SUPPLIER_TXT = "supplier.txt";
   public static final String STATION_NUMBER = "stationnumber.txt";
   public static String ip;
   public static String port;
   public static String path;
   public static String basePath;
   public static String clear_company_leave_mm_dd = "06-30";
   public static String clear_annual_leave_mm_dd = "03-31";
   public static String clear_over_mm_dd = "04-13";
   public static final int line_break_limit_dd = 16;
   public static final String SUCCESS_MSG = "操作成功！";
   public static final String FAIL_MSG = "操作失败！";
   public static final String[] FLAG = new String[]{"0", "1"};
   public static final String OS_FAIL_MSG = "系统原因，请稍后再试！";
   public static final String LOGIN_FAIL_MSG_1 = "用户名不能空！";
   public static final String LOGIN_FAIL_MSG_2 = "密码不能空！";
   public static final String LOGIN_FAIL_MSG_3 = "用户名错误！";
   public static final String LOGIN_FAIL_MSG_4 = "密码错误！";
   public static final String UPLOAD_FAIL_MSG_0 = "文件超过指定大小！";
   public static final Long UPLOAD_SIZE_1 = Long.valueOf(500000L);
   public static final Long UPLOAD_SIZE_2 = Long.valueOf(31457280L);
   public static final String UPLOAD_ACCEPT_1 = ".jpg,.JPG,.gif,.GIF,.png";
   public static final String UPLOAD_ACCEPT_2 = ".jpg,.JPG,.gif,.GIF,.png,.PNG,.pdf,.PDF,.rar,.RAR,.zip,.ZIP";
   public static final String UPLOAD_ACCEPT_3 = ".xlsx,.XLSX,.pptx,.PPTX,.rar,.RAR,.zip,.ZIP";
   public static final String UPLOAD_ACCEPT_EXCEL = ".xlsx,.XLSX";
   public static final String UPLOAD_ACCEPT_4 = ".*";
   public static final String[] day_or_hour = new String[]{"day|天", "hour|小时"};
   public static final String[] holidays_name = new String[]{"法定假日", "双休日", "工作日"};
   public static final String[] education = new String[]{"小学", "初中", "高中", "中专", "大专", "本科", "研究生", "博士", "博士以上", "无"};
   public static final String[] marry_state = new String[]{"未婚", "已婚", "已婚有子"};
   public static final String[] is_login = new String[]{"是", "否"};
   public static final String[] sex = new String[]{"男", "女"};
   public static final String[] emp01 = new String[]{"FRC", "工聘", "统嘉"};
   public static final String[] try_state = new String[]{"试用期", "转正"};
   public static final String[] flow_type = new String[]{"排班", "休假", "加班"};
   public static final String[] overtime_type = new String[]{"加班转调休", "加班转薪资"};
   public static final String[] breaktime_type = new String[]{"调休|0.5", "事假|0.5", "法定年假|4", "公司年假|4", "公出|0.5", "病假|0.5", "产假/流产假|8", "陪产假|8", "婚假|8", "丧假|8", "工伤假|8", "产前检查假|4", "哺乳假(全薪)|0.5"};
   public static final String[] schedule_type = new String[]{"计划内", "计划外"};
   public static final String[] employee_type = new String[]{"线上员工", "办公室员工"};
   public static final String[] employee_state = new String[]{"在职", "辞职", "解雇"};
   public static final String[] meals = new String[]{"早餐", "中餐", "晚餐", "夜宵"};
   public static final String[] class_name_default = new String[]{"常白班"};
   public static final String[] class_code_default = new String[]{"_changbaiban_"};
   public static final int[] flow_check_state = new int[]{0, 1, 2};
   public static final String[] flow_check_state_name = new String[]{"", "审批通过", "审批驳回"};
   public static final String[] timesheet_type = new String[]{"I", "O", "就餐"};
   public static final String[] timesheet_source = new String[]{"车闸", "三辊闸", "人工录入"};
   public static final int[] flow_check_status = new int[]{0, 1, 2};
   public static final String[] flow_check_status_name = new String[]{"待审批", "审批过程中", "审批结束"};
   public static final String login_name = "登录";
   public static final String upload_name = "上传文件";
   public static final int range = 500;
   public static final String[] default_role = new String[]{"_system_manager_role_", "_system_hr_", "_default_user_", "_sh_"};
   public static String[] employee_column = new String[]{"序号", "系统编号", "GV code", "中文姓名", "英文姓名", "部门", "项目", "GAP", "职位", "HR Status", "性别", "身份证号", "出生日期", "固定电话", "手机电话", "学历", "学校", "专业", "毕业日期", "社会工作日", "集团工作日", "公司入职日期", "试用期", "转正状态", "状态（在职 辞职 解雇）", "合同归属（FRC 工聘 统嘉）", "合同开始日期", "合同截至日期", "考勤方式（办公室or线上员工）", "离职类型", "离职日期", "薪资月份", "离职原因", "MOD/MOI", "IC卡号", "转正日期", "PIMS", "PositionSeniority", "ContractType", "年龄", "Residence", "Address", "MarryState", "紧急联系人", "紧急联系人电话"};
   public static String[] employee_leave_column = new String[]{"系统编号", "GV code", "中文姓名", "状态", "年份", "法定剩余年假", "公司剩余年假"};
   public static String[] employee_over_column = new String[]{"系统编号", "GV code", "中文姓名", "状态", "年份", "办公室剩余加班"};
   public static String[] employee_down_column = new String[]{"工号", "GV code", "中文姓名", "英文姓名", "考勤方式（办公室员工/线上员工）", "部门编码", "职位编码", "项目编码", "GAP编码", "HR Status编码", "出生日期", "性别", "身份证号", "固定电话", "手机电话", "学历", "学校", "专业", "毕业日期", "试用期（月）", "转正状态（试用期/转正）", "车牌号（没有写‘无’）", "是否登录（是/否）", "合同开始日期", "合同截至日期", "社会工作日", "集团工作日", "公司入职日期", "合同归属（FRC/工聘/统嘉）", "薪资月份", "IC卡", "MOD/MOI", "ContractType", "Residence", "Address", "MarryState（未婚/已婚/已婚有子）"};
   public static String[] employee_history_type = new String[]{"Position", "HS", "AgreementOf", "MM", "ContractType", "Deptartment", "Agreement", "Others"};
   public static String[] timesheet_detail_column = new String[]{"序号", "GV Code", "姓名", "员工类型", "GAP", "归属日期", "星期", "所属班次", "班次开始时间", "班次结束时间", "打卡开始时间", "打卡结束时间", "应出勤", "实际出勤", "缺勤小时数", "OT1 Hours", "OT2 Hours", "OT3 Hours", "中夜班个数", "备注", "刷卡次数", "应就餐次数", "实就餐次数"};
   public static String[] timesheet_detail_sum_column = new String[]{"序号", "GV Code", "姓名", "办公室/线上员工", "部门", "OT1 Hours", "OT2 Hours", "OT3 Hours", "Shift2 Number", "Shift3 Number", "应就餐次数", "实就餐次数"};
   public static String[] timesheet_column = new String[]{"中文姓名 ", "部门 ", "类型 ", "操作者 ", "打卡时间 ", "操作时间"};
   public static String[] overtime_column = new String[]{"部门 ", "申请人 ", "开始时间 ", "结束时间 ", "加班类型 ", "加班事由 ", "工作流 ", "审核人 ", "审核状态 ", "审核时间 ", "提交时间", "更新时间"};
   public static String[] breaktime_column = new String[]{"部门 ", "申请人 ", "开始时间 ", "结束时间 ", "休假时长(h) ", "休假类型 ", "休假事由 ", "工作流 ", "审核人", "审核状态 ", "审核时间 ", "提交时间", "更新时间"};
   public static String[] schedule_column = new String[]{"部门 ", "员工 ", "排班类型 ", "班次", "排班开始时间 ", "排班结束时间 ", "工作餐 ", "用餐时间 ", "加班时数 ", "备注 ", "工作流 ", "审核人 ", "审核状态 ", "审核时间 ", "提交者", "提交时间", "更新时间"};
   public static final String[] pip_item = new String[]{"Quality", "Cost", "Delivery", "Safely", "New<br/>Program", "People", "Purchasing<br/>cost"};
   public static final String[] pip_head = new String[]{"SupportHistory<br/>Data", "PlantPriority<br/>KPI", "DataAnalysis", "KPITree", "Improvement<br/>Mode", "Activity1", "Activity2", "Activity3", "Management<br/>Review", "LLS"};
   public static final int[] month = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
   public static final String[] month_en = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
   public static final String[] plant_type = new String[]{"Priority", "PIPManagement"};
   public static final String[] plant_kpi_io = new String[]{"Input", "Output"};
   public static final String[] plant_kpi_qcdp = new String[]{"Q", "C", "D", "P", "N"};
   public static final String[] plant_kpi_qcdp_name = new String[]{"QUALITY", "COST", "INVENTORY", "PEOPLE", "NEW PROGRAM"};
   public static final String[] plant_kpi_health = new String[]{"face-happy", "face-sad"};
   public static final String[] department_kpi_io = new String[]{"Input", "Output"};
   public static final String[] department_kpi_qcdp = new String[]{"Q", "C", "D", "P", "N"};
   public static final String[] department_kpi_qcdp_name = new String[]{"QUALITY", "COST", "INVENTORY", "PEOPLE", "NEW PROGRAM"};
   public static final String[] department_kpi_health = new String[]{"face-happy", "face-sad"};
   public static final String[] gap_kpi_io = new String[]{"Input", "Output"};
   public static final String[] gap_kpi_qcdp = new String[]{"Q", "C", "D", "P", "N"};
   public static final String[] gap_kpi_qcdp_name = new String[]{"QUALITY", "COST", "INVENTORY", "PEOPLE", "NEW PROGRAM"};
   public static final String[] gap_kpi_health = new String[]{"face-happy", "face-sad"};
   public static final String[] tour_level = new String[]{"Yellow", "Orange", "Red"};
   public static final String[] tour_level_name = new String[]{"黄色报警", "橙色报警", "红色报警"};
   public static final Integer[][] tour_level_color = new Integer[][]{{Integer.valueOf(255), Integer.valueOf(239), Integer.valueOf(16)}, {Integer.valueOf(241), Integer.valueOf(141), Integer.valueOf(44)}, {Integer.valueOf(228), Integer.valueOf(50), Integer.valueOf(43)}};
   public static final String[] tour_depts = new String[]{"Plant", "UAP1", "UAP2", "PCL"};
   public static final String[] change_management_type = new String[]{"人", "模具设备", "材料", "方法", "环境", "产量"};
   public static final String[] colors = new String[]{"#7ed27e", "#f5473c", "#6495ED", "#FFFFFF"};
   public static final String[] qrci_head = new String[]{"QRCI<br/>Type", "Open<br/>Date", "Problem<br/>Descripion", "Respensible", "Yesterday<br/>Task<br/>To Be Checked", "Task For<br/>Next<br/>Day/Future", "D3-24 Hour", "D6-10 Day", "D8-60 Day", "PFMEA", "CP", "LLS", "Plant<br/>Manager", "LLS1", "LLS<br/>Transversalization", "LLS Daily<br/>Tracking<br/>(30 Days)"};
   public static final String[] qrci_department_head = new String[]{"QRCI<br/>Type", "Open<br/>Date", "Problem<br/>Descripion", "Respensible", "Yesterday<br/>Task<br/>To Be Checked", "Task For<br/>Next<br/>Day/Future", "D3-24 Hour", "D6-10 Day", "D8-60 Day", "PFMEA", "CP", "LLS", "Department<br/>Manager", "LLS1", "LLS<br/>Transversalization", "LLS Daily<br/>Tracking<br/>(30 Days)"};
   public static final String[] plant_kpi_column = new String[]{"DEPARTMENT", "KPI_DATE", "KPI", "IorO", "QCDP", "TARGET", "ACTUAL", "CUM", "UNIT", "Correct/Opposite", "ISOK", "ISS DESCRIPTION", "ACTION PLAN", "RESP", "DEADLINE", "OPERATER", "分类原因I", "分类原因II", "分类原因III"};
   public static final String[] department_kpi_column = new String[]{"DEPARTMENT", "KPI_DATE", "KPI", "IorO", "QCDP", "TARGET", "ACTUAL", "CUM", "UNIT", "Correct/Opposite", "ISOK", "ISS DESCRIPTION", "ACTION PLAN", "RESP", "DEADLINE", "OPERATER", "分类原因I", "分类原因II", "分类原因III"};
   public static final String[] gap_kpi_column = new String[]{"DEPARTMENT", "KPI_DATE", "KPI", "IorO", "QCDP", "TARGET", "ACTUAL", "CUM", "UNIT", "Correct/Opposite", "ISOK", "ISS DESCRIPTION", "ACTION PLAN", "RESP", "DEADLINE", "OPERATER", "分类原因I", "分类原因II", "分类原因III"};
   public static final String[] supervisor_ranking_type = new String[]{"supervisor_header", "supervisor_body"};
   public static final String[] audit_ranking_type = new String[]{"audit_header", "audit_body"};
   public static final String[] supplier_ranking_type = new String[]{"质量最佳供应商", "质量最差供应商", "交付最佳供应商", "交付最差供应商"};
   public static final String[] supplier_down_column = new String[]{"供应商", "数据"};
   public static final String[] management_schedule = new String[]{"On Duty", "Travel", "Vacation"};
   public static final String[] level = new String[]{"Plant Level", "Department Level", "GAP Level"};
   public static final String[] skill_type = new String[]{"多岗位", "特种作业"};
   public static Map configFile = new ConcurrentHashMap();
   public static Set<Object> stationNumberSet = Collections.synchronizedSet(new HashSet<Object>());
   public static Map requestInfoMap = new ConcurrentHashMap();
   public static final String URL = "url";
   public static final String TIME = "time";
   public static List<Object> supplierList = new ArrayList<Object>();
   public static Map<Integer, MenuInfo> menuInfoMap = new ConcurrentHashMap<Integer, MenuInfo>();
   public static Map classInfoMap = new ConcurrentHashMap();
   public static Map<Integer, DepartmentInfo> departmentInfoMap = new ConcurrentHashMap<Integer, DepartmentInfo>();
   public static Map<String, DepartmentInfo> departmentCodeMap = new ConcurrentHashMap<String, DepartmentInfo>();
   public static Map<Integer, PositionInfo> positionInfoMap = new ConcurrentHashMap<Integer, PositionInfo>();
   public static Map<String, PositionInfo> positionCodeMap = new ConcurrentHashMap<String, PositionInfo>();
   public static Map projectInfoMap = new ConcurrentHashMap();
   public static Map projectCodeMap = new ConcurrentHashMap();
   public static Map gapInfoMap = new ConcurrentHashMap();
   public static Map gapCodeMap = new ConcurrentHashMap();
   public static Map hrStatusMap = new ConcurrentHashMap();
   public static Map hrStatusCodeMap = new ConcurrentHashMap();
   public static Map annualLeaveMap = new ConcurrentHashMap();
   public static Set<String> employeeZhNameSet = Collections.synchronizedSet(new HashSet<String>());
   public static Map<Integer, EmployeeInfo> employeeInfoMap = new ConcurrentHashMap<Integer, EmployeeInfo>();
   public static Map<String, EmployeeInfo> employeeCodeMap = new ConcurrentHashMap<String, EmployeeInfo>();
   public static Map<String, String> employeeCarMap = new ConcurrentHashMap<String, String>();
   public static Map<Integer, EmployeeCard> employeeCardMap = new ConcurrentHashMap<Integer, EmployeeCard>();
   public static Map<String, EmployeeCard> icCardMap = new ConcurrentHashMap<String, EmployeeCard>();


/*   static {
      Licence.checkLicence().booleanValue();
   }*/

   public static void loadData(Object obj) {
      if(obj != null) {
         if(obj instanceof EmployeeInfo) {
            EmployeeInfo pi = (EmployeeInfo)obj;
            employeeInfoMap.put(pi.getId(), pi);
            employeeCodeMap.put(pi.getEmp_code(), pi);
            employeeZhNameSet.add(pi.getZh_name());
            if(!StringUtils.isEmpty(pi.getEmp09())) {
               employeeCarMap.put(pi.getEmp09().trim(), pi.getEmp09());
            }
         }

         if(obj instanceof EmployeeCard) {
            EmployeeCard pi1 = (EmployeeCard)obj;
            employeeCardMap.put(pi1.getEmp_id(), pi1);
            icCardMap.put(pi1.getCard(), pi1);
         }

         if(obj instanceof MenuInfo) {
            MenuInfo pi2 = (MenuInfo)obj;
            menuInfoMap.put(pi2.getId(), pi2);
         }

         if(obj instanceof DepartmentInfo) {
            DepartmentInfo pi3 = (DepartmentInfo)obj;
            departmentInfoMap.put(pi3.getId(), pi3);
            departmentCodeMap.put(pi3.getDept_code(), pi3);
         }

         if(obj instanceof PositionInfo) {
            PositionInfo pi4 = (PositionInfo)obj;
            positionInfoMap.put(pi4.getId(), pi4);
            positionCodeMap.put(pi4.getPos_code(), pi4);
         }

         if(obj instanceof AnnualLeave) {
          //  (new ScheduleJob()).loadAllHrStatus();
         }
      }

   }

   public static UserInfo getUserObject(HttpServletRequest request) {
      if(request == null) {
         return null;
      } else {
         UserInfo user = (UserInfo)request.getSession().getAttribute("user");
         return user;
      }
   }
}
