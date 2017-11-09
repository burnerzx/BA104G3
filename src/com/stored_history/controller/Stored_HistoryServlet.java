package com.stored_history.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemService;
import com.stored_history.model.StoredService;
import com.stored_history.model.StoredVO;

public class Stored_HistoryServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getOne_For_Display".equals(action)){
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try{
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("stored_no");
				if(str == null || (str.trim()).length() == 0){
					errorMsgs.add("�п�J�x�ȰO���s��");
				}
				
				if (!errorMsgs.isEmpty()){
					RequestDispatcher failureView = req.getRequestDispatcher("/stored_history/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String stored_no = null;
				try{
					stored_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("�x�ȰO���s���榡�����T");
				}
				
				if (!errorMsgs.isEmpty()){
					RequestDispatcher failureView = req.getRequestDispatcher("/stored_history/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.�}�l�d�߸��*****************************************/
				StoredService storedSvc = new StoredService();
				StoredVO storedVO = storedSvc.getOneStored(stored_no);
				if (storedVO == null) {
					errorMsgs.add("�d�L���");
				}
				
				if (!errorMsgs.isEmpty()){
					RequestDispatcher failureView = req.getRequestDispatcher("/stored_history/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				
				req.setAttribute("storedVO", storedVO);
				String url = "/stored_history/listOneStored.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e){
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView =req.getRequestDispatcher("/stored_history/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update".equals(action)){// �Ӧ�listAllStored.jsp���ШD
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try{
				/***************************1.�����ШD�Ѽ�****************************************/
				
				String stored_no = new String(req.getParameter("stored_no"));
				
				/***************************2.�}�l�d�߸��****************************************/
				
				StoredService storedSvc = new StoredService();
				StoredVO storedVO = storedSvc.getOneStored(stored_no);
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				
				req.setAttribute("storedVO", storedVO);
				String url = "/stored_history/update_stored_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/stored_history/listAllStored.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update".equals(action)){// �Ӧ�update_stored_input.jsp���ШD
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try{
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String stored_no = new String(req.getParameter("stored_no").trim());
				
				String mem_no = new String(req.getParameter("mem_no").trim());
				String mem_no_reg = "^M(0-9){6}$";
				if (mem_no == null || mem_no.trim().length() == 0){
					errorMsgs.add("�|���s���ФŪť�");
				} else if(!mem_no.trim().matches(mem_no_reg)){
					errorMsgs.add("�|���s�� : �� MXXXXXX");
				}
				
				Date stored_date =null;
				try{
					stored_date = Date.valueOf(req.getParameter("stored_date").trim());
					
				} catch(IllegalArgumentException e){
					stored_date = new Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���T��� !");
				}
				
				Integer stored_type;
				try{
					stored_type = new Integer(req.getParameter("stored_type").trim());
//					String stored_type_reg = "^(1-3){1}$";
				} catch (NumberFormatException e){
					errorMsgs.add("�Эק勵�T�I�ڤ覡  (1.�I�ƥd ;2.�H�Υd ;3.�u�W�ĤT���I");
					stored_type = null;
				}
				
				Double stored_cost = null;
				try{
					stored_cost = new Double(req.getParameter("stored_cost").trim());
				} catch(NumberFormatException e){
					errorMsgs.add("�п�J���T�����B");
				}
				
				StoredVO storedVO = new StoredVO();
				
				storedVO.setStored_no(stored_no);
				storedVO.setMem_no(mem_no);
				storedVO.setStored_date(stored_date);
				storedVO.setStored_type(stored_type);
				storedVO.setStored_cost(stored_cost);
				
				if (!errorMsgs.isEmpty()){
					req.setAttribute("storedVO", storedVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/stored_history/update_stored_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				StoredService storedSvc = new StoredService();
				storedVO = storedSvc.updateStored(stored_no,mem_no,stored_date,stored_type,stored_cost);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				req.setAttribute("storedVO", storedVO);
				String url = "/stored_history/listOneStored.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e){
				errorMsgs.add("�ק��ƥ��� :" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/stored_history/update_stored_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)){// �Ӧ�addStored.jsp���ШD
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try{
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
//				String stored_no = new String(req.getParameter("stored_no").trim());
				
				String mem_no = new String(req.getParameter("mem_no").trim());
				if (mem_no == null || mem_no.trim().length() ==0 ){
					errorMsgs.add("�п�J�|���s��");
				}
				
				Date stored_date =null;
				try{
					stored_date = Date.valueOf(req.getParameter("stored_date").trim());
					
				} catch(IllegalArgumentException e){
					stored_date = new Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���T��� !");
				}
				
				Integer stored_type;
				try{
					stored_type = new Integer(req.getParameter("stored_type").trim());
//					String stored_type_reg = "^(1-3){1}$";
				} catch (NumberFormatException e){
					errorMsgs.add("�Эק勵�T�I�ڤ覡  (1.�I�ƥd ;2.�H�Υd ;3.�u�W�ĤT���I");
					stored_type = null;
				}
				
				Double stored_cost = null;
				try{
					stored_cost = new Double(req.getParameter("stored_cost").trim());
				} catch(NumberFormatException e){
					errorMsgs.add("�п�J���T�����B");
				}
				
				StoredVO storedVO = new StoredVO();
				
//				storedVO.setStored_no(stored_no);
				storedVO.setMem_no(mem_no);
				storedVO.setStored_date(stored_date);
				storedVO.setStored_type(stored_type);
				storedVO.setStored_cost(stored_cost);
				
				if (!errorMsgs.isEmpty()){
					req.setAttribute("storedVO", storedVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/stored_history/update_stored_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				StoredService storedSvc = new StoredService();
				storedVO = storedSvc.addStored(mem_no,stored_date,stored_type,stored_cost);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				req.setAttribute("storedVO", storedVO);
				String url = "/stored_history/listAllStored.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e){
				errorMsgs.add("�ק��ƥ��� :" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/stored_history/addStored.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("delete".equals(action)){
			List<String> errorMsgs =new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			
			try{
				/***************************1.�����ШD�Ѽ�***************************************/
				String stored_no = req.getParameter("stored_no");
				
				/***************************2.�}�l�R�����***************************************/
				StoredService storedSvc = new StoredService();
				storedSvc.deleteStored(stored_no);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/
				String url = "/stored_history/listAllStored.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ��� : "+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/stored_history/listAllStored.jsp");
				failureView.forward(req, res);
			}
			}
	}
}