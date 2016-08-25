package rreeggkk.nuclearsciences.common.element;

import org.apfloat.Apfloat;

import rreeggkk.nuclearsciences.common.nuclear.decay.AlphaDecay;
import rreeggkk.nuclearsciences.common.nuclear.decay.BetaDecay;
import rreeggkk.nuclearsciences.common.nuclear.decay.ITDecay;
import rreeggkk.nuclearsciences.common.nuclear.decay.MultiDecay;
import rreeggkk.nuclearsciences.common.nuclear.decay.SpecOutDecay;
import rreeggkk.nuclearsciences.common.nuclear.element.BasicElement;
import rreeggkk.nuclearsciences.common.nuclear.element.Isotope;
import rreeggkk.nuclearsciences.common.nuclear.element.MetastableIsotope;
import rreeggkk.nuclearsciences.common.util.TimeConversion;

public class ElementRegister {
	public static void register() {
		//252 has SF decay
		new BasicElement("Californium", "Cf", 98, 25) //Heat capacity not found; Law of Dulong and Petit used
			.addIsotope(new Isotope<BasicElement>(252-98).setAtomicMass(252.081626).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.645)))
			.addIsotope(new Isotope<BasicElement>(251-98).setAtomicMass(251.079587).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(900.6)))
			.addIsotope(new Isotope<BasicElement>(249-98).setAtomicMass(249.0748535).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(351)));

		//238 has SF decay
		new BasicElement("Curium", "Cm", 96, 25) //Heat capacity not found; Law of Dulong and Petit used
			.addIsotope(new Isotope<BasicElement>(248-96).setAtomicMass(248.072349).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(3.4e5)))
			.addIsotope(new Isotope<BasicElement>(247-96).setAtomicMass(247.070354).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.565e7)))
			.addIsotope(new Isotope<BasicElement>(245-96).setAtomicMass(245.0654912).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(8.5e3)));

		new BasicElement("Americium", "Am", 95, 62.7)
			.addIsotope(new Isotope<BasicElement>(243-95).setAtomicMass(243.0613811).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(7388)))
			.addIsotope(new Isotope<BasicElement>(241-95).setAtomicMass(241.0568291).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(432.7)));

		new BasicElement("Plutonium", "Pu", 94, 35.5)
			.addIsotope(new Isotope<BasicElement>(244-94).setAtomicMass(244.064204).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(8e7)))
			.addIsotope(new Isotope<BasicElement>(243-94).setAtomicMass(243.062003).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(4.9563)))
			.addIsotope(new Isotope<BasicElement>(241-94).setAtomicMass(241.0568515).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(14.290)))
			.addIsotope(new Isotope<BasicElement>(240-94).setAtomicMass(240.0538135).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(6561)))
			.addIsotope(new Isotope<BasicElement>(239-94).setAtomicMass(239.0521634).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(24100)))
			.addIsotope(new Isotope<BasicElement>(238-94).setAtomicMass(238.0495599).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(87.7)));
		
		new BasicElement("Neptunium", "Np", 93, 29.46)
			.addIsotope(new Isotope<BasicElement>(240-93).setAtomicMass(240.056162).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(1.032)))
			.addIsotope(new Isotope<BasicElement>(239-93).setAtomicMass(239.0529390).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(2.356)))
			.addIsotope(new Isotope<BasicElement>(237-93).setAtomicMass(237.0481734).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.14e6)));

		new BasicElement("Uranium", "U", 92, 27.665)
			.addIsotope(new Isotope<BasicElement>(240-92).setAtomicMass(240.056592).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(14.1)))
			.addIsotope(new Isotope<BasicElement>(239-92).setAtomicMass(239.0542933).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(23.45)))
			.addIsotope(new Isotope<BasicElement>(238-92).setAtomicMass(238.0507882).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(4.468e9)))
			.addIsotope(new Isotope<BasicElement>(237-92).setAtomicMass(237.0487302).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(6.75)))
			.addIsotope(new Isotope<BasicElement>(236-92).setAtomicMass(236.045568).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.3e7)))
			.addIsotope(new Isotope<BasicElement>(235-92).setAtomicMass(235.0439299).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(7038e7)))
			.addIsotope(new Isotope<BasicElement>(234-92).setAtomicMass(234.0409521).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(2.455e5)))
			.addIsotope(new Isotope<BasicElement>(233-92).setAtomicMass(233.0396352).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.592e5)));

		//234
		new BasicElement("Protactinium", "Pa", 91, 22.9)
			.addIsotope(new MetastableIsotope<BasicElement>(234-91, 0).setAtomicMass(234.043308).setDecay(new MultiDecay<MetastableIsotope<BasicElement>>(new BetaDecay<MetastableIsotope<BasicElement>>(), new Apfloat(0.9983), new ITDecay<MetastableIsotope<BasicElement>>(), new Apfloat(0.0016)), TimeConversion.minToTick(1.17)))
			.addIsotope(new Isotope<BasicElement>(234-91).setAtomicMass(234.043308).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(6.70)))
			.addIsotope(new Isotope<BasicElement>(233-91).setAtomicMass(233.0402473).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(26.975)))
			.addIsotope(new Isotope<BasicElement>(231-91).setAtomicMass(231.0358840).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(3.276e4)));

		new BasicElement("Thorium", "Th", 90, 26.23)
			.addIsotope(new Isotope<BasicElement>(234-90).setAtomicMass(234.043601).setDecay(new SpecOutDecay<Isotope<BasicElement>>("Protactinium-234m"), TimeConversion.dayToTick(24.10)))
			.addIsotope(new Isotope<BasicElement>(232-90).setAtomicMass(232.0380553).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.405e10)))
			.addIsotope(new Isotope<BasicElement>(231-90).setAtomicMass(231.0363043).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(25.5)))
			.addIsotope(new Isotope<BasicElement>(230-90).setAtomicMass(230.0331338).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(7.538e4)))
			.addIsotope(new Isotope<BasicElement>(229-90).setAtomicMass(229.031762).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(7340)))
			.addIsotope(new Isotope<BasicElement>(228-90).setAtomicMass(228.0287411).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.9116)))
			.addIsotope(new Isotope<BasicElement>(227-90).setAtomicMass(227.0277041).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(18.68)));

		new BasicElement("Actinium", "Ac", 89, 27.2)
			.addIsotope(new Isotope<BasicElement>(228-89).setAtomicMass(228.0310211).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(6.13)))
			.addIsotope(new Isotope<BasicElement>(227-89).setAtomicMass(227.0277521).setDecay(new MultiDecay<Isotope<BasicElement>>(new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.9861), new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.0139)), TimeConversion.yearToTick(21.772)))
			.addIsotope(new Isotope<BasicElement>(225-89).setAtomicMass(225.023230).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(10.0)));

		new BasicElement("Radium", "Ra", 88, 20.8)
			.addIsotope(new Isotope<BasicElement>(228-88).setAtomicMass(228.0310703).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(5.75)))
			.addIsotope(new Isotope<BasicElement>(226-88).setAtomicMass(226.0254098).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1600)))
			.addIsotope(new Isotope<BasicElement>(225-88).setAtomicMass(225.023612).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(14.9)))
			.addIsotope(new Isotope<BasicElement>(224-88).setAtomicMass(224.0202118).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(3.6319)))
			.addIsotope(new Isotope<BasicElement>(223-88).setAtomicMass(223.0185022).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(11.43)));

		new BasicElement("Francium", "Fr", 87, 31.2228) //Heat capacity calculated manually from 140 J/kgK specific heat capacity and 223.020 g/mol avg atomic mass
			.addIsotope(new Isotope<BasicElement>(223-87).setAtomicMass(223.0197359).setDecay(new MultiDecay<Isotope<BasicElement>>(new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.9999), new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.0001)), TimeConversion.minToTick(22)))
			.addIsotope(new Isotope<BasicElement>(221-87).setAtomicMass(221.014255).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(4.8)));

		new BasicElement("Radon", "Rn", 86, 20.786)
			.addIsotope(new Isotope<BasicElement>(222-86).setAtomicMass(222.0175777).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(3.8235)))
			.addIsotope(new Isotope<BasicElement>(220-86).setAtomicMass(220.0113940).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(55.6)))
			.addIsotope(new Isotope<BasicElement>(219-86).setAtomicMass(219.0094802).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(3.96)))
			.addIsotope(new Isotope<BasicElement>(218-86).setAtomicMass(218.0056013).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(35e-3)));

		new BasicElement("Astatine", "At", 85, 29.39818) //Heat capacity calculated manually from 140 J/kgK specific heat capacity and 209.987 g/mol avg atomic mass
			.addIsotope(new Isotope<BasicElement>(219-85).setAtomicMass(219.011162).setDecay(new MultiDecay<Isotope<BasicElement>>(new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.97), new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.03)), TimeConversion.secToTick(56)))
			.addIsotope(new Isotope<BasicElement>(218-85).setAtomicMass(218.008694).setDecay(new MultiDecay<Isotope<BasicElement>>(new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.9990), new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.0010)), TimeConversion.secToTick(1.5)))
			.addIsotope(new Isotope<BasicElement>(217-85).setAtomicMass(217.004719).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(32e-6)))
			.addIsotope(new Isotope<BasicElement>(215-85).setAtomicMass(214.998653).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(1e-4)));

		new BasicElement("Polonium", "Po", 84, 26.4)
			.addIsotope(new Isotope<BasicElement>(218-84).setAtomicMass(218.0089730).setDecay(new MultiDecay<Isotope<BasicElement>>(new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.9998), new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.0002)), TimeConversion.minToTick(3.10)))
			.addIsotope(new Isotope<BasicElement>(216-84).setAtomicMass(216.0019150).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(0.145)))
			.addIsotope(new Isotope<BasicElement>(215-84).setAtomicMass(214.9994200).setDecay(new MultiDecay<Isotope<BasicElement>>(new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.97), new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.03)), TimeConversion.secToTick(1.781e-3)))
			.addIsotope(new Isotope<BasicElement>(214-84).setAtomicMass(213.9952014).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(164.3e-6)))
			.addIsotope(new Isotope<BasicElement>(213-84).setAtomicMass(212.992857).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(3.2e-6)))
			.addIsotope(new Isotope<BasicElement>(212-84).setAtomicMass(211.9888680).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(299e-9)))
			.addIsotope(new Isotope<BasicElement>(211-84).setAtomicMass(210.9866532).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.secToTick(0.516)))
			.addIsotope(new Isotope<BasicElement>(210-84).setAtomicMass(209.9828737).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(138.376)));

		new BasicElement("Bismuth", "Bi", 83, 25.52)
			.addIsotope(new Isotope<BasicElement>(215-83).setAtomicMass(215.001770).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(7.6)))
			.addIsotope(new Isotope<BasicElement>(214-83).setAtomicMass(213.998712).setDecay(new MultiDecay<Isotope<BasicElement>>(new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.99979), new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.00021)), TimeConversion.minToTick(19.9)))
			.addIsotope(new Isotope<BasicElement>(213-83).setAtomicMass(212.994385).setDecay(new MultiDecay<Isotope<BasicElement>>(new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.9780), new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.0220)), TimeConversion.minToTick(46.5)))
			.addIsotope(new Isotope<BasicElement>(212-83).setAtomicMass(211.9912857).setDecay(new MultiDecay<Isotope<BasicElement>>(new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.6406), new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.3594)), TimeConversion.minToTick(60.55)))
			.addIsotope(new Isotope<BasicElement>(211-83).setAtomicMass(210.987269).setDecay(new MultiDecay<Isotope<BasicElement>>(new AlphaDecay<Isotope<BasicElement>>(), new Apfloat(0.9972), new BetaDecay<Isotope<BasicElement>>(), new Apfloat(0.0028)), TimeConversion.minToTick(2.14)))
			.addIsotope(new Isotope<BasicElement>(210-83).setAtomicMass(209.9841204).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.dayToTick(5.012)))
			.addIsotope(new Isotope<BasicElement>(209-83).setAtomicMass(208.9803987).setDecay(new AlphaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(1.9e19)));

		new BasicElement("Lead", "Pb", 82, 26.650)
			.addIsotope(new Isotope<BasicElement>(214-82).setAtomicMass(213.9998054).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(26.8)))
			.addIsotope(new Isotope<BasicElement>(212-82).setAtomicMass(211.9918975).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(10.64)))
			.addIsotope(new Isotope<BasicElement>(211-82).setAtomicMass(210.9887370).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(36.1)))
			.addIsotope(new Isotope<BasicElement>(210-82).setAtomicMass(209.9841885).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.yearToTick(22.20)))
			.addIsotope(new Isotope<BasicElement>(209-82).setAtomicMass(208.9810901).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.hourToTick(3.25)))
			.addIsotope(new Isotope<BasicElement>(208-82).setAtomicMass(206.9758969))
			.addIsotope(new Isotope<BasicElement>(207-82).setAtomicMass(206.9758969))
			.addIsotope(new Isotope<BasicElement>(206-82).setAtomicMass(205.9744653));

		new BasicElement("Thallium", "Tl", 81, 26.32)
			.addIsotope(new Isotope<BasicElement>(210-81).setAtomicMass(209.990074).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(1.30)))
			.addIsotope(new Isotope<BasicElement>(209-81).setAtomicMass(208.985359).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(2.161)))
			.addIsotope(new Isotope<BasicElement>(208-81).setAtomicMass(207.9820187).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(3.035)))
			.addIsotope(new Isotope<BasicElement>(207-81).setAtomicMass(206.977419).setDecay(new BetaDecay<Isotope<BasicElement>>(), TimeConversion.minToTick(4.77)))
			.addIsotope(new Isotope<BasicElement>(205-81).setAtomicMass(204.9744275));

		new BasicElement("Helium", "He", 2, 20.78)
			.addIsotope(new Isotope<BasicElement>(4-2).setAtomicMass(4.00260325415));
	}
}

