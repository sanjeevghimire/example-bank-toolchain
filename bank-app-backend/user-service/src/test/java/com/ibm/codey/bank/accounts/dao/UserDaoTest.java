package com.ibm.codey.bank.accounts.dao;

import com.ibm.codey.bank.accounts.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery typedQuery;

    @InjectMocks
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void createUser() {
        User user = new User();
        user.setSubject("Test subject");
        user.setConsentGiven(true);
        user.setDeleteRequested(false);

        doNothing().when(entityManager).persist(User.class);
        userDao.createUser(user);

        Mockito.verify(entityManager).persist(user);
    }

    @Test
    public void updateUser() {
        User updatedUser = new User();
        updatedUser.setSubject("Test subject updated");
        updatedUser.setConsentGiven(true);
        updatedUser.setDeleteRequested(false);

        when(entityManager.merge(Matchers.anyObject())).thenReturn(updatedUser);;
        User user = userDao.updateUser(updatedUser);

        Assert.assertEquals(user.getSubject(), updatedUser.getSubject());

    }

    @Test
    public void findUserByRegistryId() {
        User sampleUser = new User();
        sampleUser.setDeleteRequested(false);
        sampleUser.setConsentGiven(true);
        sampleUser.setSubject("SampleSubject");

        String subject = "SampleSubject";

//        Query query = mock(Query.class);
//        TypedQuery typedQuery = mock(TypedQuery.class);
        when(typedQuery.getSingleResult()).thenReturn(sampleUser);
        when(typedQuery.setParameter(Matchers.anyString(), Matchers.anyObject())).thenReturn(typedQuery);
        when(entityManager.createNamedQuery("User.findUserByRegistryId",User.class)).thenReturn(typedQuery);

        User user = userDao.findUserByRegistryId(subject);

//        Mockito.verify(entityManager).createNamedQuery("User.findUserByRegistryId");
//        Mockito.verify(query).getSingleResult();
        Assert.assertEquals(subject, user.getSubject());

    }
}