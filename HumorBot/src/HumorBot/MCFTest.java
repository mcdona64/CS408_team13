package HumorBot;

import org.junit.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;

public class MCFTest {
    @Test
    public void TestMCFThroawayMultipleBlanks() {
        MCF mcf = new MCF("Test");
        ArrayList<WhiteCard> m = new ArrayList<WhiteCard>();
        for (int i = 0; i < 6; i++)
            m.add(new WhiteCard("placeholder" + i, i, i + 3));
        mcf.hardCodeHand(m);
        try {
            ArrayList<Integer> res = mcf.assessHandMultipleBlanks(3, true);
            assert (m.size() == 6);
            assert (res.size() == 3);
            for (int i = 0; i < 3; i++)
                assert (res.get(i) == i);
        } catch (CardCountException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestMCFMultExceptionTooMany() {
        MCF mcf = new MCF("Test");
        ArrayList<WhiteCard> m = new ArrayList<WhiteCard>();
        try {
            ArrayList<Integer> res = mcf.assessHandMultipleBlanks(3, true);
            assert (false);
        } catch (CardCountException e) {
            assert (true);
            e.printStackTrace();
        }
    }

    @Test
    public void TestMCFMultExceptionNotEnough() {
        MCF mcf = new MCF("Test");
        ArrayList<WhiteCard> m = new ArrayList<WhiteCard>();
        for (int i = 0; i < 6; i++)
            m.add(new WhiteCard("placeholder" + i, i, i + 3));
        mcf.hardCodeHand(m);
        try {
            ArrayList<Integer> res = mcf.assessHandMultipleBlanks(1, true);
            assert (false);
        } catch (CardCountException e) {
            assert (true);
            e.printStackTrace();
        }
    }

    @Test
    public void TestNormalRoundObvious() {
        MCF mcf = new MCF("Test");
        ArrayList<WhiteCard> m = new ArrayList<WhiteCard>();
        for (int i = 0; i < 6; i++)
            m.add(new WhiteCard("placeholder" + i, i, i + 3));
        mcf.hardCodeHand(m);
        assert (mcf.assessHandNormal() == 5);
    }

    @Test
    public void TestHandThrowawayObvious() {
        MCF mcf = new MCF("Test");
        ArrayList<WhiteCard> m = new ArrayList<WhiteCard>();
        for (int i = 0; i < 6; i++)
            m.add(new WhiteCard("placeholder" + i, i, i + 3));
        mcf.hardCodeHand(m);
        assert (mcf.assessHandThrowaway() == 0);
    }

    @Test
    public void TestMCFNormalMultipleBlanks() {
        MCF mcf = new MCF("Test");
        ArrayList<WhiteCard> m = new ArrayList<WhiteCard>();
        for (int i = 0; i < 6; i++)
            m.add(new WhiteCard("placeholder" + i, i, i + 3));
        mcf.hardCodeHand(m);
        try {
            ArrayList<Integer> res = mcf.assessHandMultipleBlanks(3, false);
            assert (m.size() == 6);
            assert (res.size() == 3);
            for (int i = 0; i < 3; i++)
                assert (res.get(i) == i + 3);
        } catch (CardCountException e) {
            e.printStackTrace();
        }
    }

}
