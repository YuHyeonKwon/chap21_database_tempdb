package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** product 테이블에 상품 추가
 *  카테고리: 식료품
 *  상품ID: 21
 *  상품명: 시금치
 *  가격: 3500
 *  입고일: 2023-02-10 
 *  **/
public class Database04_Insert01 {

	public static void main(String[] args) {

		// 오라클 드라이버 로딩 문자
		String driver = "oracle.jdbc.driver.OracleDriver";
		
		// 데이터베이스 연결 문자열
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		
		// 데이터베이스 계정명
		String dbId = "tempdb";
		
		// 데이터베이스 비밀번호
		String dbPwd = "1234";
		
		Connection con = null;			// 데이터베이스 연결 객체
		PreparedStatement ps = null;	// 커넥션 객체를 통해서 데이터베이스에 쿼리를 실행해주는 객체
		ResultSet rs = null;			// 실행된 쿼리문의 결과를 반환 받는 객체
		
		String sql;
		
		try {
			// 1. 드라이버 로딩
			Class.forName(driver);
			System.out.println("1. 드라이버 로드 성공!");
			
			// 2. 데이터베이스 커넥션(연결)
			con = DriverManager.getConnection(url,dbId,dbPwd);
			System.out.println("2. 커넥션 객체 생성 성공!");
			
			// 3. 테이블에 ? 문자가 포함된 insert 문에 넣을 인자 생성
			
			int productId = 21;
			String productName = "시금치";
			int price = 3500;
			int categoryID = 5;
			String hiredate = "20230210";
			
			// 4. 저장 SQL문 생성하기
			sql = "insert into product(product_id, product_name, price, category_id, receipt_date) values(?, ?, ?, ?, to_date(?,'yyyy/mm/dd'))";
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, productId);
			ps.setString(2, productName);
			ps.setInt(3, price);
			ps.setInt(4, categoryID);
			ps.setString(5, hiredate);
			
			int result = ps.executeUpdate();
			if (result > 0) {
				System.out.println("저장 성공");
			}else {
				System.out.println("저장 실패");
			}
			
		}catch (ClassNotFoundException e) {
			System.out.println("드라이버 ERR: " + e.getMessage());
		}catch (SQLException e) {
			System.out.println("SQL ERR: " + e.getMessage());
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			}catch (SQLException e) {
				System.out.println("자원해제 ERR: " + e.getMessage());
			}
		}
	} // main e

} // class e
