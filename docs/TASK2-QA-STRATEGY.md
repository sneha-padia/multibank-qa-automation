# Task 2 - QA Strategy & Thinking

## The scenario

> You've just joined a fintech startup as a QA Engineer. On day one you're handed a mobile
> trading app, for both iOS and Android, that's two weeks from its first public release. There's
> no existing test suite, no QA documentation, and the dev team has been shipping fast.
> Assume real user funds are involved.

Answer the following in your own words:

## 1. Where do you start?

Since there is no existing test suite or QA documentation, my first priority would be to understand the application and identify the biggest risks as early as possible. I would do this by getting an understanding of the product from the BA, PO and the development team, and by gathering information on what has been achieved so far compared to what is planned for the release.

As this is the first public release, there is no previous version to compare against, so I would use whatever the team has already produced, such as tickets, user stories, designs and API documentation, to understand the intended behavior. I would also review any existing bug lists and error logs to see where problems are already showing up. Alongside this, I would conduct exploratory testing on both iOS and Android to familiarize myself with the product’s functionality.

Because real user funds are involved, I would focus on identifying the biggest showstoppers and the risks if anything broke on release day, starting with the flows that move money: deposits, withdrawals, placing buy and sell orders, and account balances.

While gathering this information, I would put together a plan that aligns the scope already covered with what remains to be achieved within the given timeframe. By the end of the first week, I would share a prioritized risk list and a basic smoke test checklist for the money-related flows with the team, so we agree early on what must work before release.

## 2. How would you approach testing this app?

Keeping the timeline in mind, I would create a traceability matrix to map requirements to risk and functionality, and then proceed with validation in priority order.

I would work on the showstopper features first, and bugs in these areas would be prioritized: the user registration flow, the add money flow, the withdrawal flow, and the buy and sell flow. Because real funds are involved, I would test not only that these flows work, but also what happens when things go wrong, for example insufficient balance, a double tap on “Buy”, or losing the connection in the middle of a trade, and check that balances and transaction history always match. I would run risk analysis and performance testing in parallel, and include basic security checks such as login, session handling, and making sure users can only see their own account data.

The app runs on both iOS and Android, so I would test the money flows on real devices covering the most common phone models and OS versions either on actual device or simulators. Anything that is not part of the showstopper functionality can be moved to a lower priority and addressed in upcoming releases. I would also recommend feature flags, so a feature can be switched off without a full rollback if it causes problems after release.

As there is no existing test suite, I would start building a small automation suite for the most critical money flows and run it on every new build. This saves time and makes sure new development does not break modules that are already working, while exploratory and edge-case testing stays manual in the first two weeks.

## 3. What does QA look like inside a sprint, from ticket creation through to regression?

As soon as a ticket is created, the first thing I would do is make sure the acceptance criteria are clear and testable, review them and estimate the QA effort accordingly. Any preconditions or assumptions must be added to the ticket.

When sprint planning starts I'll make sure every story picked up has its QA effort estimated and is planned accordingly. While development is in progress, I would complete the test cases and test data preparation which reflects my scenario with dummy data not actual.

Once a story is handed over for testing, I would perform a sanity test to make sure the build has the functionality ready to be tested. Once sanity passes, I would proceed with test case validation. I would log bugs for failed scenarios, sharing the evidence, test data, steps to reproduce, build details and severity.I would keep the test suite in sync with my testing progress so it always reflects the correct status and update the team in the daily stand-up to discuss progress towards the sprint goal.

Once a storys validation is complete, I would make sure a peer review is done before moving it to the PO’s queue, all QA tasks are closed and the evidence is attached. I would also add automated tests for the completed story, so the regression suite grows every sprint.

When a feature is ready for go-live, I would make sure regression, backward compatibility and rollback validations are completed. At the end of each sprint, we demo what was achieved and hold a retrospective on what went well, what went wrong and what can be improved to make the next sprint more productive, including where bugs were found, so we can catch them earlier next time.

## 4. My ideal regression suite

It will consist of following:

- **Smoke test:** quickly covering the critical flow of each module/functionality. This runs automatically on every new build and finishes within a few minutes, so a broken build is caught straight away.
- **Critical business flows:** end-to-end journeys such as login, registration, payments and order processing. As these are the features customers use every day, they are run as a daily regression, so any break is caught within a day.
- **Existing functionality:** flows covering existing functionality in detail. When a new enhancement is built on an existing feature, this suite is rerun to make sure the change doesn’t break anything that already works.
- **High-risk areas and defect clustering:** as this is the first release, I would use the bugs found during testing and in each sprint to identify where defects cluster, and add more tests in those areas to reduce production bugs. Every fixed bug also gets a test, so it doesn’t come back.

I would also keep optimizing the regression suite, as it can become outdated. With every new feature added, it should be updated, and tests for removed features retired.

Also automate as much of the regression suite as possible to give more coverage in less time and ensures the existing code remains steady.

## 5. What would keep you up at night about this app specifically and releasing to the public?

- **App crashes:** if the app crashes in the middle of something important like placing an order or adding money and user is left unsure. I would test the key flows on the most common iOS and Android devices, and check what the user sees when they reopen the app after a crash.

- **Money movement flows:** deposits, withdrawals and buy/sell orders are where a bug directly costs a customer money. I would make sure these flows are part of every regression run, and that balances and transaction history always match. Any bug here is a release blocker.

- **Real-time updates not happening:** a trading app depends on live prices and balances. If prices are delayed or stop updating, users may buy or sell at a price they didn’t expect. I would test what the app shows when the live feed is slow or stops. (While testing the public website for Task 1, I found coins with falling prices listed under “Gainers”, which shows how easily market data can mislead users.)

- **Performance estimates going wrong:** if more users arrive at launch than the performance testing planned for, or the market moves sharply and everyone trades at once, the app could slow down or fail exactly when people need it. I would run performance tests above the expected peak load, not just at it.

- **Security:** stolen accounts mean stolen funds. I would make sure login, two-factor authentication and session timeouts are tested, and push for a security review before launch.
- **Users seeing only their own data:** a user must never be able to see another customer’s balance, trades or personal details. This would be a serious privacy and trust issue. I would test that each user can only access their own account data, including by trying to reach another user’s data directly.

- **Payment failures in between:** if a payment fails halfway, for example the money is taken but the balance doesn’t update, or the order is placed but not shown, the customer loses trust immediately. I would test interrupted payments (network loss, timeouts, app closed) and check that the final status is always correct and the money is either completed or returned.

- **Failure logs not being recorded:** if errors aren’t logged, we can’t trace what went wrong when a customer reports a problem, and we may not even know there is a problem. Before release, I would make sure failures are logged and that the team gets alerted on errors and failed transactions.
