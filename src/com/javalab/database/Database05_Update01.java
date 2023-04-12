package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 상품 가격 변경(수정)
 * 탱크로리 상품의 가격을 500,000으로 수정하시오.
 */
public class Database05_Update01 {
	
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
		
		try {
			// 1. 드라이버 로딩
			Class.forName(driver);
			System.out.println("1. 드라이버 로드 성공!");
			
			// 2. 데이터베이스 커넥션(연결)
			con = DriverManager.getConnection(url,dbId,dbPwd);
			System.out.println("2. 커넥션 객체 생성 성공!");
			
			// 3. update
			int price = 500000;
			String name = "탱크로리";
			
			String sql = "update product set price = ?";
			sql += " where product_name = ?";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, price);
			ps.setString(2, name);
			
			int result = ps.executeUpdate();
			
			if (result > 0) {
				System.out.println("수정 성공");
			}else {
				System.out.println("수정 실패");
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
