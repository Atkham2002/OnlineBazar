//package uz.nt.firstspring.repository.impl;
//
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Repository;
//import uz.nt.firstspring.entity.Student;
//
//import java.util.List;
//
//@Repository
//public class StudentRepositoryImpl{
//
//    @Autowired
//    private SessionFactory sessionFactory;
//    @Qualifier("postgres")
//    @Autowired
//    private Session session;
//
//    public List<Student> getAllHQL(){
//        try {
//            Query query = session.createQuery("select t from Student t");
//            return query.getResultList();
//        }catch (HibernateException e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<Student> getAllNative(){
//        Session session = sessionFactory.openSession();
//
//        Query<Student> query = session.createNativeQuery("select id, first_name, course, age, birth_date from Student", Student.class);
//
//        return query.getResultList();
//    }
//
//    public List<Student> getAllNamed(){
//        Session session = sessionFactory.openSession();
//
//        Query<Student> query = session.createNamedQuery("findAll", Student.class);
//
//        return query.getResultList();
//    }
//}
