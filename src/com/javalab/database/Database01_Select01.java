package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/*
 * 1.(표준 쿼리) 전 상품의 카테고리명과 상품명, 가격, 입고일자를 출력하되
 * 	카테고리로 오름차순, 상품ID 내림차순 정렬하시오.
 */
public class Database01_Select01 {

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
			System.out.println("드라이버 로드 성공");
			
			// 2. 데이터베이스 커넥션(연결)
			con = DriverManager.getConnection(url, dbId, dbPwd);
			System.out.println("커넥션 객체 생성 완료!");
			
			// 3. preparsStatement 객체를 통해서 쿼리하기 위한
			//	  SQL 쿼리 문장 만들기 (삽입,수정,삭제,조회)
			
			sql = "select c.category_name, p.product_id, p.product_name, p.price, p.receipt_date";
			sql += " from category c left outer join product p on c.category_id = p.category_id";
			sql += " order by c.category_id, p.product_id desc";
					
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				System.out.println( rs.getString("category_name") + "\t" +
									rs.getInt("product_id") + "\t" +
									rs.getString("product_name") + "\t" +
									rs.getInt("price") + "\t" +
									rs.getDate("receipt_date"));
			}
			
			// 5. Statement 객체의 executeQuery() 메소드를 통해서 쿼리 실행
			//	   데이터 베이스에서 조회된 결과가 ResultSet 객체에 담겨옴
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
				System.out.println("자원반납 ERR!: " + e.getMessage());
			}
		}
	} // main e

} // class e
