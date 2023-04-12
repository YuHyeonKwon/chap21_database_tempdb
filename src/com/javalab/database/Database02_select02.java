package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** 가격이 25,000원 이상인 상품들의 이름과 가격을 조회하시오. **/
public class Database02_select02 {

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
			Class.forName(driver);
			System.out.println("드라이버 로드 성공");
			
			con = DriverManager.getConnection(url,dbId,dbPwd);
			System.out.println("커넥션 객체 생성 완료");
			System.out.println();
			
			int wherePrice = 25000;
			
			// 생성한 PreparedStatement 객체를 통해서 쿼리하기 위한 SQL 쿼리 문장 만들기 (삽입,수정,삭제,조회)
			sql = "select p.product_name, p.price";
			sql += " from product p";
			sql += " where p.price >= ?";
			sql += " order by p.price desc";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, wherePrice);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getString("product_name") + "\t" +
										rs.getInt("price"));
			}
		}catch (ClassNotFoundException e) {
			System.out.println("드라이버 ERR!: " + e.getMessage());
		}catch (SQLException e) {
			System.out.println("SQL ERR!: " + e.getMessage());
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
				System.out.println("자원해제 ERR!: " + e.getMessage());
			}
		}
	} // main e

} // class e
