# Test Plan – Mobile Trading App (first public release)

## 1. Objective
Make sure the app is safe to release to the public in two weeks, with the focus on the flows that
move real user funds, account security and data privacy.

## 2. Scope
**In scope**
- Registration and identity checks (KYC)
- Login, two-factor authentication and sessions
- Add money (deposit) and withdraw money
- Buy and sell orders
- Balance, portfolio and transaction history
- Live price updates
- Users can only see their own data
- App stability and performance under expected peak load
- iOS and Android

**Out of scope for this release** (lower priority, planned for later releases)
- Low-risk features such as profile picture and settings updates (basic checks only)
- Full accessibility audit
- Testing on older or rarely used devices and OS versions

## 3. Approach
- **Risk-based:** features are tested in order of priority from the risk matrix (`docs/RISK-MATRIX.md`). Critical money flows first.
- **Traceability:** a traceability matrix maps each requirement to its risk and its test cases, so nothing critical is missed.
- **Functional and negative testing:** check that features work, and what happens when things go wrong (insufficient balance, double tap on "Buy", connection lost mid-trade).
- **Reconciliation:** after every money transaction, balances and transaction history must match.
- **Exploratory testing:** manual testing around new and changed areas.
- **Security:** login, two-factor authentication, session timeout, and data access between users.
- **Performance:** load tests above the expected launch peak.
- **Automation:** a small automated smoke suite for the money flows, run on every new build, growing each sprint into the regression suite.

## 4. Test environment and devices
- Testing on a test/staging environment with test accounts and known balances. Never real customer funds.
- Real devices covering the most common iOS and Android phones and OS versions (list to be agreed with the team based on expected users).

## 5. Test data
- Test accounts with known balances for each scenario (e.g. enough funds, zero balance, just below an order amount).
- Separate accounts to test that one user can't see another user's data.

## 6. Entry criteria (when testing a build can start)
- The build is deployed to the test environment.
- The story has clear, testable acceptance criteria.
- The sanity check passes (the functionality to be tested is present and working at a basic level).

## 7. Exit criteria (when we're ready to release)
- All Critical and High priority test cases executed.
- No open Critical bugs; no open bug that shows a wrong amount, or loses or duplicates money.
- Any remaining High bugs reviewed and accepted by the PO, with a plan to fix.
- Regression (including all money flows) passed on both iOS and Android.
- Error logging and alerts confirmed working.

## 8. Defect management
- Bugs are logged with evidence, test data, steps to reproduce, build details, severity and priority.
- Any money-related bug is a release blocker.
- Fixed bugs are retested, and a regression test is added so they don't come back.

## 9. Schedule (two weeks)
| When | What |
|---|---|
| Days 1–3 | Understand the product, review tickets, logs and known bugs; risk matrix and test plan agreed |
| Days 3–8 | Test Critical and High priority flows on both platforms; build the automated smoke suite |
| Days 8–11 | Security, performance and edge-case testing; retest fixes |
| Days 12–13 | Full regression on both platforms |
| Day 14 | Go / no-go recommendation |

## 10. Roles
- **QA:** test plan, test cases, testing, bug reports, automation, go/no-go recommendation
- **Developers:** unit tests, fixing bugs, supporting test environments
- **PO / BA:** clarifying requirements and acceptance criteria, accepting remaining risks
- **Security / DevOps:** security review, performance environment, logging and monitoring

## 11. Deliverables
- Test plan (this document) and risk matrix
- Test cases and traceability matrix
- Bug reports
- Automated smoke and regression suite
- Test summary report and release readiness checklist